/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app_controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import app_dao.UtilisateurDAO;
import app_helper.Session;
import app_model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import app_dao.VenteDAO;
import app_dao.DepenseDAO;
import app_dao.ApprovisionnementDAO;
import app_dao.ArticleDAO;
import app_dao.TypeArticleDAO;
import app_dao.FactureDAO;

/**
 * FXML Controller class
 *
 * @author axlnx
 */
public class LoginController implements Initializable {

    @FXML
    private TextField nom_utilisateur_text_field;
    @FXML
    private TextField mot_de_passe_text_field;

    /**
     * Initializes the controller class.
     */

    // DAO utilisés après connexion (réutilisés, fournis par Main)
    private UtilisateurDAO utilisateurDAO;
    private VenteDAO venteDAO;
    private DepenseDAO depenseDAO;
    private ApprovisionnementDAO approvisionnementDAO;
    private ArticleDAO articleDAO;
    private TypeArticleDAO typeArticleDAO;
    private FactureDAO factureDAO;
    public void setUtilisateurDAO_login(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    public void setAllDAO(VenteDAO venteDAO, DepenseDAO depenseDAO, ApprovisionnementDAO approvisionnementDAO,
                          ArticleDAO articleDAO, TypeArticleDAO typeArticleDAO, FactureDAO factureDAO,
                          UtilisateurDAO utilisateurDAO) {
        this.venteDAO = venteDAO;
        this.depenseDAO = depenseDAO;
        this.approvisionnementDAO = approvisionnementDAO;
        this.articleDAO = articleDAO;
        this.typeArticleDAO = typeArticleDAO;
        this.factureDAO = factureDAO;
        this.utilisateurDAO = utilisateurDAO;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void se_connecter(ActionEvent event) throws SQLException {
        //verification entree utilisateur
        Utilisateur utilisateurConnecter = utilisateurDAO.loginUtilisateur(nom_utilisateur_text_field.getText(), mot_de_passe_text_field.getText());
        if (utilisateurConnecter != null) {
            Session.getInstance().setUtilisateur(utilisateurConnecter);
            // Naviguer vers le tableau de bord
            try {
                FXMLLoader loaderTableau = new FXMLLoader(getClass().getResource("/app_fxml/tableau_bord.fxml"));
                Parent rootTableau = loaderTableau.load();
                Tableau_bordController tableauController = loaderTableau.getController();
                // Injection des DAO nécessaires
                tableauController.setDAO_TableauBord(venteDAO, depenseDAO, approvisionnementDAO);
                tableauController.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
                Stage stage = (Stage) nom_utilisateur_text_field.getScene().getWindow();
                stage.setScene(new Scene(rootTableau));
                stage.centerOnScreen();
            } catch (IOException e) {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setTitle("Erreur d'affichage");
                err.setHeaderText(null);
                err.setContentText("Impossible de charger le tableau de bord.");
                err.showAndWait();
            }
        } else {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("Erreur de connexion");
            err.setHeaderText(null);
            err.setContentText("Nom d'utilisateur ou mot de passe incorrect.");
            err.showAndWait();
        }
    }



}
