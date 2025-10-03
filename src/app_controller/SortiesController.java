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
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author axlnx
 */
public class SortiesController implements Initializable {

    @FXML
    private TableView<?> depnse_tableview;
    @FXML
    private Label valeur_depense_label;
    @FXML
    private TableView<?> appprovisionnement_tableview;
    @FXML
    private Label valeur_approvisionnement_label;
    @FXML
    private Label total_sortie_label;

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
    private void historiqque_depense(ActionEvent event) {
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) {
    }

    @FXML
    private void entrees_caise(ActionEvent event) {
    }

    @FXML
    private void sorties_caise(ActionEvent event) {
    }

    @FXML
    private void caise(ActionEvent event) {
    }

    @FXML
    private void utilisateur(ActionEvent event) {
    }

    @FXML
    private void deconnexion(ActionEvent event) {
    }
    
}
