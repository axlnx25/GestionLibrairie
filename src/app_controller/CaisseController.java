/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
public class CaisseController implements Initializable {

    @FXML
    private Label total_sorties_label;
    @FXML
    private Label total_entrees_label;
    @FXML
    private Label resultat_caisse_label;

    /**
     * Initializes the controller class.
     */
    private VenteDAO venteDAO;
    private DepenseDAO depenseDAO;
    private ApprovisionnementDAO approvisionnementDAO;

    public void setDAO_Caisse(VenteDAO venteDAO, DepenseDAO depenseDAO, ApprovisionnementDAO approvisionnementDAO) {
        this.venteDAO = venteDAO;
        this.depenseDAO = depenseDAO;
        this.approvisionnementDAO = approvisionnementDAO;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            int totalEntrees = venteDAO.getTotalToutesVente();
            int totalDepenses = depenseDAO.getTotalDepense();
            int totalAppros = approvisionnementDAO.getTotalApprovisionnement();

            int totalSorties = totalDepenses + totalAppros;
            int resultat = totalEntrees - totalSorties;

            total_entrees_label.setText("TOTAL ENTREES : " + totalEntrees );
            total_sorties_label.setText("TOTAL SORTIES : " + totalSorties );
            resultat_caisse_label.setText("BILAN CAISSE : " + resultat);

            // Changer couleur du fond selon gain ou perte
            if (resultat >= 0) {
                resultat_caisse_label.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
            } else {
                resultat_caisse_label.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void afficher_tableau_de_bord(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Stage stage = (Stage) total_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_vente.fxml"));
        Stage stage = (Stage) total_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Stage stage = (Stage) total_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void approvisionnement(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/approvisionnement.fxml"));
        Stage stage = (Stage) total_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void consulter_stock(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/stock_nouvelle_article.fxml"));
        Stage stage = (Stage) total_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Stage stage = (Stage) total_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Stage stage = (Stage) total_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/entrees.fxml"));
        Stage stage = (Stage) total_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void sorties_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/sorties.fxml"));
        Stage stage = (Stage) total_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/caisse.fxml"));
        Stage stage = (Stage) total_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Stage stage = (Stage) total_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/login.fxml"));
        Stage stage = (Stage) total_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    
}
