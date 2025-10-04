package app_dao;

import app_model.Type_Article;

import java.sql.*;
import java.util.ArrayList;

public class TypeArticleDAO {
    private Connection connection;

    public TypeArticleDAO(Connection connection) {
        this.connection = connection;
    }

    public void ajouterTypeArticleBDD (Type_Article type_article) throws SQLException {
        String sql = "INSERT INTO Type_Article (type, code_utilisateur) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, type_article.getTypeArticle());
        statement.setInt(2, type_article.getCodeUtilisateur());

        statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();

        if (rs.next()) {
            type_article.setIdTypeArticle(rs.getInt(1));
        }
    }

    public void modifierTypeArticleBDD (Type_Article type_article) throws SQLException {
        String sql = "UPDATE Type_Article SET type = ?, code_utilisateur = ? WHERE id_type = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, type_article.getTypeArticle());
        statement.setInt(2, type_article.getCodeUtilisateur());
        statement.setInt(3, type_article.getIdTypeArticle());

        statement.executeUpdate();
    }

    public void supprimerTypeArticleBDD (Type_Article type_article) throws SQLException {
        String sql = "DELETE FROM Type_Article WHERE id_type = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, type_article.getIdTypeArticle());
        statement.executeUpdate();
    }

    public ArrayList<Type_Article> obtenirTypeArticleBDD () throws SQLException {
        ArrayList<Type_Article> type_articles = new ArrayList<>();
        String sql = "SELECT * FROM Type_Article";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Type_Article t = new Type_Article(
                    rs.getString("type"),
                    rs.getInt("code_utilisateur")
            );
            t.setIdTypeArticle(rs.getInt("id_type"));
            type_articles.add(t);
        }
        return type_articles;
    }
}
