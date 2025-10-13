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
        // Étape 1 — Récupérer l’id du type d’article depuis la table TypeArticle
        String sqlType = "SELECT id_type FROM Type_Article WHERE type = ?";
        PreparedStatement psType = connection.prepareStatement(sqlType);
        psType.setString(1, article.getTypeArticle());
        ResultSet rsType = psType.executeQuery();

        int idTypeArticle = -1;
        if (rsType.next()) {
            idTypeArticle = rsType.getInt("id_type");
        } else {
            throw new SQLException("Type d'article introuvable : " + article.getTypeArticle());
        }

        // Étape 2 — Insérer l’article avec cet id
        String sql = "INSERT INTO Article (designation_article, quantite_article, prix_vente_article, code_utilisateur, type_article) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, article.getNomArticle());
        statement.setInt(2, article.getQuantiteArticle());
        statement.setInt(3, article.getPrixVenteArticle());
        statement.setInt(4, article.getCodeUtilisateur());
        statement.setInt(5, idTypeArticle);

        statement.executeUpdate();

        // Étape 3 — Récupérer l’id généré pour l’article
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            article.setIdArticle(rs.getInt(1));
        }
    }


    public void modifierArticleBDD(Article article) throws SQLException {
        // Récupérer l’id du type d’article
        String sqlType = "SELECT id_type FROM Type_Article WHERE type = ?";
        PreparedStatement psType = connection.prepareStatement(sqlType);
        psType.setString(1, article.getTypeArticle());
        ResultSet rsType = psType.executeQuery();

        int idTypeArticle = -1;
        if (rsType.next()) {
            idTypeArticle = rsType.getInt("id_type");
        } else {
            throw new SQLException("Type d'article introuvable : " + article.getTypeArticle());
        }

        // Mettre à jour l’article
        String sql = "UPDATE Article SET designation_article = ?, quantite_article = ?, prix_vente_article = ?, code_utilisateur = ?, type_article = ? WHERE id_article = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, article.getNomArticle());
        statement.setInt(2, article.getQuantiteArticle());
        statement.setInt(3, article.getPrixVenteArticle());
        statement.setInt(4, article.getCodeUtilisateur());
        statement.setInt(5, idTypeArticle);
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

        String sql = """
        SELECT a.id_article, a.designation_article, a.quantite_article, a.prix_vente_article, 
               a.code_utilisateur, t.type AS nom_type_article
        FROM Article a
        JOIN Type_Article t ON a.type_article = t.id_type
    """;

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Article a = new Article(
                    rs.getInt("quantite_article"),
                    rs.getInt("prix_vente_article"),
                    rs.getString("designation_article"),
                    rs.getString("nom_type_article"),  // on récupère le nom du type
                    rs.getInt("code_utilisateur")
            );
            a.setIdArticle(rs.getInt("id_article"));
            articles.add(a);
        }

        return articles;
    }

    public void diminuerQuantiteArticle(int idArticle, int quantiteVendue) throws SQLException {
        String sql = "UPDATE Article SET quantite_article = quantite_article - ? WHERE id_article = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, quantiteVendue);
        ps.setInt(2, idArticle);
        ps.executeUpdate();
    }

    public void augmenterQuantiteArticle(int idArticle, int quantiteAjoutee) throws SQLException {
        String sql = "UPDATE Article SET quantite_article = quantite_article + ? WHERE id_article = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, quantiteAjoutee);
        ps.setInt(2, idArticle);
        ps.executeUpdate();
    }



}
