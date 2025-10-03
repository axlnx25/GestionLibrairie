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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author axlnx
 */
public class Historique_depenseController implements Initializable {

    @FXML
    private DatePicker date_debut_historique_depense;
    @FXML
    private DatePicker date_fin_historique_depense;
    @FXML
    private TableView<?> historique_depense_table_view;
    @FXML
    private TableColumn<?, ?> colonne_motif;
    @FXML
    private TableColumn<?, ?> colonne_montant;
    @FXML
    private TableColumn<?, ?> colonne_date;
    @FXML
    private Label valeur_depense_label;

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
    private void filtrer_historique_depense(ActionEvent event) {
    }
    
}
