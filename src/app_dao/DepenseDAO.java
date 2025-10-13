package app_dao;

import app_model.Depense;
import app_model.Vente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DepenseDAO {
    private Connection connection;

    public DepenseDAO(Connection connection) {
        this.connection = connection;
    }

    public void ajouterDepenseBDD(Depense depense) throws SQLException {
        String sql = "INSERT INTO Depense (designation_depense, montant_depense, date_depense, numero_utilisateur) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, depense.getDesignationDepense());
        statement.setInt(2, depense.getMontantDepense());
        statement.setDate(3, java.sql.Date.valueOf(depense.getDateDepense()));
        statement.setInt(4, depense.getCodeUtilisateur());

        statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            depense.setIdDepense(rs.getInt(1));
        }
    }

    public void modifierDepenseBDD(Depense depense) throws SQLException {
        String sql = "UPDATE Depense SET designation_depense = ?, montant_depense = ?, date_depense = ?,  numero_utilisateur = ? WHERE id_depense = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, depense.getDesignationDepense());
        statement.setInt(2, depense.getMontantDepense());
        statement.setDate(3, java.sql.Date.valueOf(depense.getDateDepense()));
        statement.setInt(4, depense.getCodeUtilisateur());
        statement.setInt(5, depense.getIdDepense());

        statement.executeUpdate();
    }

    public void supprimerDepenseBDD(Depense depense) throws SQLException {
        String sql = "DELETE FROM Depense WHERE id_depense = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, depense.getIdDepense());
        statement.executeUpdate();
    }

    public ArrayList<Depense> listerDepenseBDD() throws SQLException {
        ArrayList<Depense> depenses = new ArrayList<>();
        String sql = "SELECT * FROM Depense";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Depense d = new Depense(
                    rs.getString("designation_depense"),
                    rs.getInt("montant_depense"),
                    rs.getDate("date_depense").toLocalDate(),
                    rs.getInt("numero_utilisateur")
            );
            d.setIdDepense(rs.getInt("id_depense"));
            depenses.add(d);
        }
        return depenses;
    }

    public ArrayList<Depense> listerDepensefiltrer(LocalDate debut, LocalDate fin) throws SQLException {
        ArrayList<Depense> depenses = new ArrayList<>();

        String sql ="SELECT * FROM Depense WHERE date_depense BETWEEN ? AND ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setDate(1, java.sql.Date.valueOf(debut));
        statement.setDate(2, java.sql.Date.valueOf(fin));

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Depense d = new Depense(
                    rs.getString("designation_depense"),
                    rs.getInt("montant_depense"),
                    rs.getDate("date_depense").toLocalDate(),
                    rs.getInt("numero_utilisateur")
            );
            d.setIdDepense(rs.getInt("id_depense"));
            depenses.add(d);
        }
        return depenses;
    }

    // Total de toutes les dépenses
    public int getTotalDepense() throws SQLException {
        String sql = "SELECT SUM(montant_depense) AS total FROM Depense";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            return rs.getInt("total"); // retourne la somme totale
        }
        return 0; // si aucune dépense
    }

    // Total des dépenses entre deux dates
    public int getTotalDepenseFiltre(LocalDate debut, LocalDate fin) throws SQLException {
        String sql = "SELECT SUM(montant_depense) AS total FROM Depense WHERE date_depense BETWEEN ? AND ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, java.sql.Date.valueOf(debut));
        statement.setDate(2, java.sql.Date.valueOf(fin));
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            // si aucune dépense dans la période, rs.getInt("total") renvoie 0
            return rs.getInt("total");
        }
        return 0;
    }

    public int getNombreDepensesDuJour(LocalDate date) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Depense WHERE DATE(date_depense) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public int getTotalDepensesDuJour(LocalDate date) throws SQLException {
        String sql = "SELECT COALESCE(SUM(montant_depense),0) FROM Depense WHERE DATE(date_depense) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    private ObservableList<Depense> depenses = FXCollections.observableArrayList();

    public ObservableList<Depense> getDepensesObservable() {
        return depenses;
    }



}
