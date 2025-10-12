package app_dao;

import app_model.Approvisionnement;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ApprovisionnementDAO {
    private Connection connection;

    public ApprovisionnementDAO (Connection conn) {
        this.connection = conn;
    }

    public void ajouterApprovisionnementBDD(Approvisionnement approvisionnement) throws SQLException {
        String sql ="INSERT INTO Approvisionnement (stock_origine, quantite_approvisionnement, date_approvisionnement, montant_approvisionnement, numero_utilisateur, numero_article) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, approvisionnement.getStockOrigine());
        statement.setInt(2, approvisionnement.getQuantiteApprovisionne());
        statement.setDate(3, java.sql.Date.valueOf(approvisionnement.getDateApprovisionnement()));
        statement.setInt(4, approvisionnement.getMontantApprovisionne());
        statement.setInt(5, approvisionnement.getCodeUtilisateur());
        statement.setInt(6, approvisionnement.getNumeroArticle());

        statement.executeUpdate();
        ResultSet idMysql = statement.getGeneratedKeys();
        if (idMysql.next()) {
            approvisionnement.setIdApprovisionnement(idMysql.getInt(1));
        }
    }

    public void modifierApprovisionnementBDD(Approvisionnement approvisionnement) throws SQLException {
        String sql = "UPDATE Approvisionnement SET stock_origine = ?, quantite_approvisionnement = ?, date_approvisionnement = ?, montant_approvisionnement = ?, numero_utilisateur = ?, numero_article = ? WHERE id_approvisionnement = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, approvisionnement.getStockOrigine());
        statement.setInt(2, approvisionnement.getQuantiteApprovisionne());
        statement.setDate(3, java.sql.Date.valueOf(approvisionnement.getDateApprovisionnement()));
        statement.setInt(4, approvisionnement.getMontantApprovisionne());
        statement.setInt(5, approvisionnement.getCodeUtilisateur());
        statement.setInt(6, approvisionnement.getNumeroArticle());
        statement.setInt(7, approvisionnement.getIdApprovisionnement());

        statement.executeUpdate();
    }

    public void supprimerApprovisionnementBDD(Approvisionnement approvisionnement) throws SQLException {
        String sql ="DELETE FROM Approvisionnement WHERE id_approvisionnement = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, approvisionnement.getIdApprovisionnement());
        statement.executeUpdate();
    }

    public ArrayList<Approvisionnement> obtenirApprovisionnementsBDD() throws SQLException {
        ArrayList<Approvisionnement> approvisionnements = new ArrayList<>();
        String sql = "SELECT * FROM Approvisionnement";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs =  statement.executeQuery();

        while (rs.next()) {
            Approvisionnement a = new Approvisionnement(
                    rs.getString("stock_origine"),
                    rs.getInt("quantite_approvisionnement"),
                    rs.getDate("date_approvisionnement").toLocalDate(),
                    rs.getInt("montant_approvisionnement"),
                    rs.getInt("numero_article"),
                    rs.getInt("numero_utilisateur")
            );
            a.setIdApprovisionnement(rs.getInt("id_approvisionnement"));
            approvisionnements.add(a);
        }
        return approvisionnements;
    }

    public int getTotalApprovisionnement() throws SQLException {
        String sql = "SELECT SUM(montant_approvisionnement) AS total FROM Approvisionnement";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            return rs.getInt("total"); // retourne la somme totale
        }
        return 0; // si aucun approvisionnement
    }

    public int getNombreApprovisionnementsDuJour(LocalDate date) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Approvisionnement WHERE DATE(date_approvisionnement) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public int getTotalApprovisionnementsDuJour(LocalDate date) throws SQLException {
        String sql = "SELECT COALESCE(SUM(montant_approvisionnement),0) FROM Approvisionnement WHERE DATE(date_approvisionnement) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }



}
