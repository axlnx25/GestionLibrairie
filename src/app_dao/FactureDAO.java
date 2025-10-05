package app_dao;

import app_DTO.FactureDTO;
import app_model.Facture;

import java.sql.*;
import java.util.ArrayList;

public class FactureDAO {
    private Connection connection;

    public FactureDAO(Connection connection) {
        this.connection = connection;
    }

    public void ajouterFactureBDD (Facture facture) throws SQLException {
        String sql ="INSERT INTO Facture (id_vente, id_article, quantite_vendu) VALUES (?,?,?)";
        PreparedStatement statement  = connection.prepareStatement(sql);
        statement.setInt(1, facture.getIdVente());
        statement.setInt(2, facture.getIdArticle());
        statement.setInt(3,facture.getQuantite());
        statement.executeUpdate();
    }

    public void supprimerFactureBDD (Facture facture) throws SQLException {
        String sql ="DELETE FROM Facture WHERE id_vente = ? AND id_article = ?";
        PreparedStatement statement  = connection.prepareStatement(sql);
        statement.setInt(1, facture.getIdVente());
        statement.setInt(2, facture.getIdArticle());
        statement.executeUpdate();
    }

    public ArrayList<FactureDTO> listerFactureParIdVente(int idVente) throws SQLException {
        ArrayList<FactureDTO> details = new ArrayList<>();
        String sql = "SELECT a.designation_article, f.id_article, v.quantite_vendu, " +
                "a.prix_vente_article, v.date_vente, v.remise_vente " +
                "FROM Facture f " +
                "JOIN Article a ON f.id_article = a.id_article " +
                "JOIN Vente v ON f.id_vente = v.id_vente " +
                "WHERE f.id_vente = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idVente);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            details.add(new FactureDTO(
                    idVente,
                    rs.getInt("id_article"),
                    rs.getString("designation_article"),
                    rs.getInt("quantite_vendu"),
                    rs.getDate("date_vente").toLocalDate(),
                    rs.getInt("prix_vente_article"),
                    rs.getInt("remise_vente")
            ));
        }
        return details;
    }


}
