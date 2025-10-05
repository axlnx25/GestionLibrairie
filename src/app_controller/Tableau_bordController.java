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

import app_dao.ApprovisionnementDAO;
import app_dao.DepenseDAO;
import app_dao.VenteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author axlnx
 */
public class Tableau_bordController implements Initializable {

    @FXML
    private Label nombre_vente_label;
    @FXML
    private Label total_vente_label;
    @FXML
    private Label nombre_depense_label;
    @FXML
    private Label total_depense_label;
    @FXML
    private Label total_encaisse_label;

    /**
     * Initializes the controller class.
     */
    private VenteDAO venteDAO;
    private DepenseDAO depenseDAO;
    private ApprovisionnementDAO approvisionnementDAO;

    public void setDAO_TableauBord(VenteDAO venteDAO, DepenseDAO depenseDAO, ApprovisionnementDAO approvisionnementDAO) {
        this.venteDAO = venteDAO;
        this.depenseDAO = depenseDAO;
        this.approvisionnementDAO = approvisionnementDAO;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            LocalDate today = LocalDate.now();

            // Ventes
            int nbVentes = venteDAO.getNombreVentesDuJour(today);
            int totalVentes = venteDAO.getTotalVentesDuJour(today);

            // Dépenses
            int nbDepenses = depenseDAO.getNombreDepensesDuJour(today);
            int totalDepenses = depenseDAO.getTotalDepensesDuJour(today);

            // Approvisionnements
            int nbAppro = approvisionnementDAO.getNombreApprovisionnementsDuJour(today);
            int totalAppro = approvisionnementDAO.getTotalApprovisionnementsDuJour(today);

            // Encaisse = ventes - (dépenses + appro)
            int encaisse = totalVentes - (totalDepenses + totalAppro);

            // Mise à jour des labels
            nombre_vente_label.setText("NOMBRE DE VENTE AUJOURD' HUI : " + nbVentes);
            total_vente_label.setText("MONTANT VENTE AUJOURD' HUI : " + totalVentes);

            nombre_depense_label.setText("NOMBRE DE DEPENSE/APPROVISIONNEMENT AUJOURD' HUI : " + (nbDepenses + nbAppro)); // total opérations sortantes
            total_depense_label.setText("MONTANT VENTE AUJOURD' HUI : " + (totalDepenses + totalAppro));

            total_encaisse_label.setText("MONTANT ENTRÉ EN CAISSE AUJOURD' HUI : " + encaisse);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void afficher_tableau_de_bord(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Stage stage = (Stage) nombre_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_vente.fxml"));
        Stage stage = (Stage) nombre_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Stage stage = (Stage) nombre_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void approvisionnement(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/approvisionnement.fxml"));
        Stage stage = (Stage) nombre_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void consulter_stock(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/stock_nouvelle_article.fxml"));
        Stage stage = (Stage) nombre_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Stage stage = (Stage) nombre_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Stage stage = (Stage) nombre_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/entrees.fxml"));
        Stage stage = (Stage) nombre_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void sorties_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/sorties.fxml"));
        Stage stage = (Stage) nombre_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/caisse.fxml"));
        Stage stage = (Stage) nombre_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Stage stage = (Stage) nombre_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/login.fxml"));
        Stage stage = (Stage) nombre_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
