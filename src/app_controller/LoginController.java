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

    UtilisateurDAO utilisateurDAO;
    public void setUtilisateurDAO_login(UtilisateurDAO utilisateurDAO) {
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
            //acces a l'app
        } else {
            //appel alerte erreur login password
        }
    }



}
