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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author axlnx
 */
public class Stock_nouvelle_articleController implements Initializable {

    @FXML
    private ComboBox<?> filtre_type_article_combobox;
    @FXML
    private TableView<?> stock_tableview;
    @FXML
    private TableColumn<?, ?> colonne_article;
    @FXML
    private TableColumn<?, ?> colonne_quantite;
    @FXML
    private TableColumn<?, ?> colonne_prix_vente;
    @FXML
    private Label valeur_stock_label;
    @FXML
    private TextField article_textfield;
    @FXML
    private TextField quantite_textfield;
    @FXML
    private TextField prix_vente_textfield;
    @FXML
    private ComboBox<?> type_article_combobox;

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
    private void bouton_filtre(ActionEvent event) {
    }

    @FXML
    private void ajouter_type_article(ActionEvent event) {
    }

    @FXML
    private void ajouter_article(ActionEvent event) {
    }

    @FXML
    private void modifier_article(ActionEvent event) {
    }

    @FXML
    private void supprimer_article(ActionEvent event) {
    }
    
}
