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
        String sql ="INSERT INTO Facture (id_vente, id_article) VALUES (?,?)";
        PreparedStatement statement  = connection.prepareStatement(sql);
        statement.setInt(1, facture.getIdVente());
        statement.setInt(2, facture.getIdArticle());
        statement.executeUpdate();
    }

    public void supprimerFactureBDD (Facture facture) throws SQLException {
        String sql ="DELETE FROM Facture WHERE id_vente = ? AND id_article = ?";
        PreparedStatement statement  = connection.prepareStatement(sql);
        statement.setInt(1, facture.getIdVente());
        statement.setInt(2, facture.getIdArticle());
        statement.executeUpdate();
    }

    public ArrayList<FactureDTO> listerFactureBDD() throws SQLException {
        ArrayList<FactureDTO> listeFacture = new ArrayList<>();
        String sql ="SELECT f.id_vente, f.id_article, a.designation_article, v.quantite_vendu" +
                    "v.date_vente, a.prix_vente_article, v.remise_vente" +
                    "FROM Facture f" +
                    "JOIN Vente v ON f.id_vente = v.id_vente" +
                    "JOIN Article a ON f.id_article = a.id_article";
        PreparedStatement statement  = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            listeFacture.add(new FactureDTO(
                    resultSet.getInt("id_vente"),
                    resultSet.getInt("id_article"),
                    resultSet.getString("designation_article"),
                    resultSet.getInt("quantite_vendu"),
                    resultSet.getDate("date_vente").toLocalDate(),
                    resultSet.getInt("prix_vente_article"),
                    resultSet.getInt("remise_vente")
            ));
        }
        return listeFacture;
    }
}
