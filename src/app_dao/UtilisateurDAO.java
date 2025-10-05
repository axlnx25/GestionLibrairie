package app_dao;

import app_model.Utilisateur;

import java.sql.*;
import java.util.ArrayList;

public class UtilisateurDAO {
    private Connection connection;

    public UtilisateurDAO(Connection conn) {
        this.connection = conn;
    }

    public Utilisateur loginUtilisateur(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Utilisateur WHERE nom_utilisateur = ?, mot_de_passe = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Utilisateur u = new Utilisateur(
                    resultSet.getString("nom_utilisateur"),
                    resultSet.getString("role_utilisateur"),
                    resultSet.getString("mot_de_passe")
            );
            u.setIdUtilisateur(resultSet.getInt("id_utilisateur"));
            return u;
        } else  {
            return null;
        }
    }

    public void ajouterUtilisateurBDD(Utilisateur utilisateur) throws SQLException {
        String sql = "INSERT INTO Utilisateur (nom_utilisateur, role_utilisateur, mot_de_passe) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, utilisateur.getNomUtilisateur());
        statement.setString(2, utilisateur.getRoleUtilisateur());
        statement.setString(3, utilisateur.getMotDePasse());

        statement.executeUpdate();
        ResultSet idMysql = statement.getGeneratedKeys();
        if (idMysql.next()) {
            utilisateur.setIdUtilisateur(idMysql.getInt(1));
        }
    }

    public void modifierUtilisateurBDD(Utilisateur utilisateur) throws SQLException {
        String sql = "UPDATE Utilisateur SET nom_utilisateur = ?, role_utilisateur = ?, mot_de_passe = ? WHERE id_utilisateur = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, utilisateur.getNomUtilisateur());
        statement.setString(2, utilisateur.getRoleUtilisateur());
        statement.setString(3, utilisateur.getMotDePasse());
        statement.setInt(4, utilisateur.getIdUtilisateur());

        statement.executeUpdate();
    }

    public void supprimerUtilisateurBDD(Utilisateur utilisateur) throws SQLException {
        String sql = "DELETE FROM Utilisateur WHERE id_utilisateur = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, utilisateur.getIdUtilisateur());
        statement.executeUpdate();
    }

    public ArrayList<Utilisateur> obenirUtilisateurBDD() throws SQLException {
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM Utilisateur";
        PreparedStatement  statement = connection.prepareStatement(sql);

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Utilisateur u = new Utilisateur(
                    rs.getString("nom_utilisateur"),
                    rs.getString("role_utilisateur"),
                    rs.getString("mot_de_passe")
            );
            u.setIdUtilisateur(rs.getInt("id_utilisateur"));
            utilisateurs.add(u);
        }
        return utilisateurs;
    }
}
