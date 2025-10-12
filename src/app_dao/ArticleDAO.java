package app_dao;

import app_model.Article;

import java.sql.*;
import java.util.ArrayList;

public class ArticleDAO {
    private Connection connection;

    public ArticleDAO(Connection conn) {
        this.connection = conn;
    }

    public void ajouterArticleBDD(Article article) throws SQLException {
        String sql ="INSERT INTO Article (designation_article, quantite_article, prix_vente_article, code_utilisateur, type_article) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, article.getNomArticle());
        statement.setInt(2, article.getQuantiteArticle());
        statement.setInt(3, article.getPrixVenteArticle());
        statement.setInt(4, article.getCodeUtilisateur());
        statement.setString(5, article.getTypeArticle());
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            article.setIdArticle(rs.getInt(1));
        }
    }

    public void modifierArticleBDD(Article article) throws SQLException {
        String sql = "UPDATE Article SET designation_article = ?, quantite_article = ?, prix_vente_article = ?, code_utilisateur = ?, type_article = ? WHERE id_article = ? ";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, article.getNomArticle());
        statement.setInt(2, article.getQuantiteArticle());
        statement.setInt(3, article.getPrixVenteArticle());
        statement.setInt(4, article.getCodeUtilisateur());
        statement.setString(5, article.getTypeArticle());
        statement.setInt(6, article.getIdArticle());

        statement.executeUpdate();
    }

    public void supprimerArticleBDD(Article article) throws SQLException {
        String sql = "DELETE FROM Article WHERE id_article = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, article.getIdArticle());
        statement.executeUpdate();
    }

    public ArrayList<Article> obtenirArticleBDD() throws SQLException {
        ArrayList<Article> articles = new ArrayList<>();
        String sql ="SELECT * FROM Article";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Article a = new Article(
                    rs.getInt("quantite_article"),
                    rs.getInt("prix_vente_article"),
                    rs.getString("designation_article"),
                    rs.getString("type_article"),
                    rs.getInt("code_utilisateur")
            );
            a.setIdArticle(rs.getInt("id_article"));
            articles.add(a);
        }
        return articles;
    }

}
