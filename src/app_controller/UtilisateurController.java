/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app_controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author axlnx
 */
public class UtilisateurController implements Initializable {

    @FXML
    private TableView<?> utilisateur_tableview;
    @FXML
    private TableColumn<?, ?> colonne_nom;
    @FXML
    private TableColumn<?, ?> colonne_mot_de_passe;
    @FXML
    private TableColumn<?, ?> colonne_role;
    @FXML
    private TextField nom_textfied;
    @FXML
    private TextField mot_de_passe_textfield;
    @FXML
    private TextField role_textfield;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void afficher_tableau_de_bord(ActionEvent event) {
    }

    @FXML
    private void nouvelle_vente(ActionEvent event) {
    }

    @FXML
    private void historique_vente(ActionEvent event) {
    }

    @FXML
    private void approvisionnement(ActionEvent event) {
    }

    @FXML
    private void consulter_stock(ActionEvent event) {
    }

    @FXML
    private void historique_depense(ActionEvent event) {
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) {
    }

    @FXML
    private void entrees_caisse(ActionEvent event) {
    }

    @FXML
    private void sorties_caisse(ActionEvent event) {
    }

    @FXML
    private void caisse(ActionEvent event) {
    }

    @FXML
    private void utilisateur(ActionEvent event) {
    }

    @FXML
    private void deconnexion(ActionEvent event) {
    }

    @FXML
    private void ajouter_utilisateur(ActionEvent event) {
    }

    @FXML
    private void modifier_utilisateur(ActionEvent event) {
    }

    @FXML
    private void supprimer_utilisateur(ActionEvent event) {
    }
    
}
