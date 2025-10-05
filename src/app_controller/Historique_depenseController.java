/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import app_dao.DepenseDAO;
import app_helper.ValidationEntree;
import app_model.Depense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

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
    private TableView<Depense> historique_depense_table_view;
    @FXML
    private TableColumn<Depense, String> colonne_motif;
    @FXML
    private TableColumn<Depense, Integer> colonne_montant;
    @FXML
    private TableColumn<Depense, LocalDate> colonne_date;
    @FXML
    private Label valeur_depense_label;

    /**
     * Initializes the controller class.
     */

    private DepenseDAO depenseDAO;
    public void setDepenseDAO_HistoriqueDepense(DepenseDAO depenseDAO) {
        this.depenseDAO = depenseDAO;
    }
    ObservableList<Depense> depenseObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        historique_depense_table_view.setItems(depenseObservableList);

        colonne_motif.setCellValueFactory(data -> data.getValue().designationDepenseProperty());
        colonne_montant.setCellValueFactory(data -> data.getValue().montantDepenseProperty().asObject());
        colonne_date.setCellValueFactory(data -> data.getValue().dateDepenseProperty());

        try {
            valeur_depense_label.setText("TOTAL DEPENSE : " + depenseDAO.getTotalDepense());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }    

    @FXML
    private void afficher_tableau_de_bord(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_vente.fxml"));
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void approvisionnement(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/approvisionnement.fxml"));
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void consulter_stock(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/stock_nouvelle_article.fxml"));
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/entrees.fxml"));
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void sorties_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/sorties.fxml"));
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/caisse.fxml"));
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/login.fxml"));
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void filtrer_historique_depense(ActionEvent event) throws SQLException {
        boolean okDebut = ValidationEntree.validerDateObligatoire(date_debut_historique_depense);
        boolean okFin = ValidationEntree.validerDateObligatoire(date_fin_historique_depense);
        if (okDebut && okFin) {
            historique_depense_table_view.getItems().clear();
            depenseObservableList.clear();

            depenseObservableList.setAll(depenseDAO.listerDepensefiltrer(date_debut_historique_depense.getValue(), date_fin_historique_depense.getValue()));
            historique_depense_table_view.setItems(depenseObservableList);

            valeur_depense_label.setText("TOTAL DEPENSE : " + depenseDAO.getTotalDepenseFiltre(date_debut_historique_depense.getValue(), date_fin_historique_depense.getValue()));
        } else {
            //appel alerte date erreur
        }

    }

    public void chargerDepense() throws SQLException {
        if (depenseDAO != null) {
            depenseObservableList.setAll(depenseDAO.listerDepenseBDD());
        }
    }
    
}
