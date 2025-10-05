/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import app_dao.DepenseDAO;
import app_helper.Session;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author axlnx
 */
public class Nouvelle_depenseController implements Initializable {

    @FXML
    private TextField motif_depense_textfield;
    @FXML
    private DatePicker date_depense;
    @FXML
    private TextField montant_depense;

    /**
     * Initializes the controller class.
     */

    private DepenseDAO depenseDAO;
    public void setDepenseDAO_NouvelleDepense(DepenseDAO depense) {
        this.depenseDAO = depense;
    }
    ObservableList<Depense> listDepenseObservable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void afficher_tableau_de_bord(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_vente.fxml"));
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void approvisionnement(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/approvisionnement.fxml"));
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void consulter_stock(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/stock_nouvelle_stock.fxml"));
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/entrees.fxml"));
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void sorties_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/sorties.fxml"));
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/caisse.fxml"));
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/login.fxml"));
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void ajouter_depense(ActionEvent event) throws SQLException {
        boolean okMotif = ValidationEntree.validerTexteObligatoire(motif_depense_textfield);
        boolean okMontant = ValidationEntree.validerNombre(montant_depense);
        boolean okDate = ValidationEntree.validerDateObligatoire(date_depense);
        if (okMotif && okMontant && okDate) {
            Depense d = new Depense(
                    motif_depense_textfield.getText(),
                    Integer.parseInt(montant_depense.getText()),
                    date_depense.getValue(),
                    Session.getInstance().getIDUtilisateur()
            );
            listDepenseObservable.add(d);
            depenseDAO.ajouterDepenseBDD(d);

            motif_depense_textfield.clear();
            montant_depense.clear();
            date_depense.setValue(null);
        } else {
            //appel alerte verifier les champs
        }
    }

    @FXML
    private void annuler_depense(ActionEvent event) {
        motif_depense_textfield.clear();
        montant_depense.clear();
        date_depense.setValue(null);
    }

    public void chargerDepense() throws SQLException {
        if (depenseDAO != null) {
            listDepenseObservable.setAll(depenseDAO.listerDepenseBDD());
        }
    }
    
}
