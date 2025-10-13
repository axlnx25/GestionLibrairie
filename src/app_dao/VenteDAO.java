package app_dao;

import app_model.Vente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class VenteDAO {
    private Connection connection;

    private ObservableList<Vente> ventes = FXCollections.observableArrayList();

    public ObservableList<Vente> getVentesObservable() {
        return ventes;
    }


    public VenteDAO(Connection conn) {
        this.connection = conn;
    }

    public void ajouterVenteBDD(Vente vente) throws SQLException {
        String sql = "INSERT INTO Vente (quantite_vendu, date_vente, remise_vente, numero_utilisateur) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, vente.getQuantiteVendu());
        statement.setDate(2, java.sql.Date.valueOf(vente.getDateVente()));
        statement.setInt(3, vente.getRemiseVente());
        statement.setInt(4, vente.getCodeUtilisateur());

        statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            vente.setIdVente(rs.getInt(1));
        }
    }

    public void supprimerVenteBDD(int id) throws SQLException {
        String sql = "DELETE FROM Vente WHERE id_vente = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public ArrayList<Vente> obtenirVentesBDD() throws SQLException {
        ArrayList<Vente> ventes = new ArrayList<>();
        String sql = "SELECT * FROM Vente";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Vente v = new Vente(
                    rs.getInt("quantite_vendu"),
                    rs.getDate("date_vente").toLocalDate(),
                    rs.getInt("remise_vente"),
                    rs.getInt("numero_utilisateur")
            );
            v.setIdVente(rs.getInt("id_vente"));
            ventes.add(v);
        }
        return ventes;
    }

    public ArrayList<Vente> filtrerVenteDate(LocalDate debut, LocalDate fin) throws SQLException {
        String sql = "SELECT * FROM Vente WHERE date_vente BETWEEN ? AND ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, java.sql.Date.valueOf(debut));
        statement.setDate(2, java.sql.Date.valueOf(fin));
        ResultSet rs = statement.executeQuery();
        ArrayList<Vente> ventes = new ArrayList<>();
        while (rs.next()) {
            Vente v = new Vente(
                    rs.getInt("quantite_vendu"),
                    rs.getDate("date_vente").toLocalDate(),
                    rs.getInt("remise_vente"),
                    rs.getInt("numero_utilisateur")
            );
            v.setIdVente(rs.getInt("id_vente"));
            ventes.add(v);
        }
        return ventes;
    }

    public int getRemiseVente(int idVente) throws SQLException {
        String sql = "SELECT remise_vente FROM Vente WHERE id_vente = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idVente);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return rs.getInt("remise_vente");
        }
        return 0;
    }

    public int getTotalVente(int id_vente) throws SQLException {
        String sql = "SELECT f.quantite_vendu, a.prix_vente_article, v.remise_vente " +
                "FROM Facture f " +
                "JOIN Article a ON f.id_article = a.id_article " +
                "JOIN Vente v ON f.id_vente = v.id_vente " +
                "WHERE f.id_vente = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id_vente);
        ResultSet rs = statement.executeQuery();

        int total = 0;
        int remise = 0;
        while (rs.next()) {
            total += rs.getInt("quantite_vendu") * rs.getInt("prix_vente_article");
            remise = rs.getInt("remise_vente"); // même valeur sur toutes les lignes
        }
        total -= remise; // soustraire la remise totale UNE seule fois
        return total;
    }


    public int getTotalToutesVente() throws SQLException {
        String sql = "SELECT f.id_vente, f.quantite_vendu, a.prix_vente_article, v.remise_vente " +
                "FROM Facture f " +
                "JOIN Article a ON f.id_article = a.id_article " +
                "JOIN Vente v ON f.id_vente = v.id_vente";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        int total = 0;
        int currentVenteId = -1;
        int totalParVente = 0;
        int remise = 0;

        while (rs.next()) {
            int idVente = rs.getInt("id_vente");

            if (idVente != currentVenteId) {
                // Nouvelle vente → ajouter l'ancienne à total
                if (currentVenteId != -1) {
                    total += totalParVente - remise;
                }
                // Initialisation pour la nouvelle vente
                currentVenteId = idVente;
                totalParVente = 0;
                remise = rs.getInt("remise_vente");
            }

            totalParVente += rs.getInt("quantite_vendu") * rs.getInt("prix_vente_article");
        }

        // Ajouter la dernière vente
        if (currentVenteId != -1) {
            total += totalParVente - remise;
        }

        return total;
    }

    public int getNombreVentesDuJour(LocalDate date) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Vente WHERE DATE(date_vente) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public int getTotalVentesDuJour(LocalDate date) throws SQLException {
        // Calcule en s'appuyant sur getTotalVente pour chaque vente du jour
        int total = 0;
        for (Vente v : filtrerVenteDate(date, date)) {
            total += getTotalVente(v.getIdVente());
        }
        return total;
    }





}
