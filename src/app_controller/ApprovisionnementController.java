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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author axlnx
 */
public class ApprovisionnementController implements Initializable {

    @FXML
    private TableView<?> approvisionnement_tableview;
    @FXML
    private TableColumn<?, ?> colonne_fournisseur;
    @FXML
    private TableColumn<?, ?> colonne_article;
    @FXML
    private TableColumn<?, ?> colonne_quantite;
    @FXML
    private TableColumn<?, ?> colonne_montant;
    @FXML
    private TableColumn<?, ?> colonne_date;
    @FXML
    private ComboBox<?> fournisseur_combo_box;
    @FXML
    private ComboBox<?> article_combo_box;
    @FXML
    private TextField quantite_text_field;
    @FXML
    private TextField montant_text_field;
    @FXML
    private DatePicker date_text_field;

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
    private void menu_utilisateur(ActionEvent event) {
    }

    @FXML
    private void deconnexion(ActionEvent event) {
    }

    @FXML
    private void ajouter_approvisionnement(ActionEvent event) {
    }

    @FXML
    private void modifier_approvisionnement(ActionEvent event) {
    }

    @FXML
    private void supprimer_approvisionnement(ActionEvent event) {
    }
    
}
