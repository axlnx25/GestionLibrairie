package app_dao;

import app_model.Vente;

import java.sql.*;
import java.util.ArrayList;

public class VenteDAO {
    private Connection connection;

    public VenteDAO(Connection conn) {
        this.connection = conn;
    }

    public void ajouterVente(Vente vente) throws SQLException {
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
            v.setIdVente(rs.getInt("idVente"));
            ventes.add(v);
        }
        return ventes;
    }

}
