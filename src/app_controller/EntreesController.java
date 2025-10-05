/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import app_DTO.EntreeDTO;
import app_dao.VenteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author axlnx
 */
public class EntreesController implements Initializable {

    @FXML
    private TableView<EntreeDTO> entrees_caisse_table_view;
    @FXML
    private TableColumn<EntreeDTO, Integer> colnne_facture;
    @FXML
    private TableColumn<EntreeDTO, Integer> colnne_montant;
    @FXML
    private Label valeur_entrees_label;

    /**
     * Initializes the controller class.
     */
    private VenteDAO venteDAO;
    private ObservableList<EntreeDTO> entrees = FXCollections.observableArrayList();

    public void setVenteDAO_Entrees(VenteDAO venteDAO) {
        this.venteDAO = venteDAO;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colnne_facture.setCellValueFactory(new PropertyValueFactory<>("idVente"));
        colnne_montant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        entrees_caisse_table_view.setItems(entrees);

        try {
            chargerEntrees();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void afficher_tableau_de_bord(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Stage stage = (Stage) valeur_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_vente.fxml"));
        Stage stage = (Stage) valeur_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Stage stage = (Stage) valeur_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void approvisionnement(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/approvisionnement.fxml"));
        Stage stage = (Stage) valeur_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void consulter_stock(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/stock_nouvelle_article.fxml"));
        Stage stage = (Stage) valeur_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Stage stage = (Stage) valeur_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Stage stage = (Stage) valeur_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/entrees.fxml"));
        Stage stage = (Stage) valeur_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void sorties_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/sorties.fxml"));
        Stage stage = (Stage) valeur_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/caisse.fxml"));
        Stage stage = (Stage) valeur_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Stage stage = (Stage) valeur_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/login.fxml"));
        Stage stage = (Stage) valeur_entrees_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private void chargerEntrees() throws SQLException {
        entrees.clear();
        int total = 0;

        for (var vente : venteDAO.obtenirVentesBDD()) {
            int montant = venteDAO.getTotalVente(vente.getIdVente());
            entrees.add(new EntreeDTO(vente.getIdVente(), montant));
            total += montant;
        }

        valeur_entrees_label.setText("TOTAL ENTREES : " + total );
    }
    
}
