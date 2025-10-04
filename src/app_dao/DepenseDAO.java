package app_dao;

import app_model.Depense;

import java.sql.*;
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
}
