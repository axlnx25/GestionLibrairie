/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app_DTO.FactureDTO;
import app_dao.*;
import app_helper.ValidationEntree;
import app_model.Vente;
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
public class Historique_venteController implements Initializable {

    @FXML
    private DatePicker date_debut_historique_facture;
    @FXML
    private DatePicker date_fin_historique_facture;
    @FXML
    private TableView<Vente> facture_table_view;
    @FXML
    private TableColumn<Vente, LocalDate> colonne_date;
    @FXML
    private TableColumn<Vente, Integer> colonne_facture;
    @FXML
    private Label montant_facture_label;
    @FXML
    private TableView<FactureDTO> details_tableview;
    @FXML
    private TableColumn<FactureDTO, String> colonne_article;
    @FXML
    private TableColumn<FactureDTO, Integer> colonne_quantite;
    @FXML
    private TableColumn<FactureDTO, Integer> colonne_prix_unitaire;
    @FXML
    private Label remise_facture_label;
    @FXML
    private Label montant_vente_label;

    /**
     * Initializes the controller class.
     */

    private VenteDAO venteDAO;
    private DepenseDAO depenseDAO;
    private ApprovisionnementDAO approvisionnementDAO;
    private ArticleDAO articleDAO;
    private TypeArticleDAO typeArticleDAO;
    private FactureDAO factureDAO;
    private UtilisateurDAO utilisateurDAO;

    public void setAllDAO(VenteDAO venteDAO, DepenseDAO depenseDAO, ApprovisionnementDAO approvisionnementDAO,
                          ArticleDAO articleDAO, TypeArticleDAO typeArticleDAO, FactureDAO factureDAO,
                          UtilisateurDAO utilisateurDAO) {
        this.venteDAO = venteDAO;
        this.depenseDAO = depenseDAO;
        this.approvisionnementDAO = approvisionnementDAO;
        this.articleDAO = articleDAO;
        this.typeArticleDAO = typeArticleDAO;
        this.factureDAO = factureDAO;
        this.utilisateurDAO = utilisateurDAO;
    }

    public void setFactureDAO_HistoriqueVente(FactureDAO factureDAO) {
        this.factureDAO = factureDAO;
    }
    public void setVenteDAO_HistoriqueVente(VenteDAO venteDAO) {
        this.venteDAO = venteDAO;
    }
    ObservableList<Vente> vente_observable_list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        facture_table_view.setItems(vente_observable_list);

        colonne_date.setCellValueFactory(data -> data.getValue().dateVenteProperty());
        colonne_facture.setCellValueFactory(data -> data.getValue().idVenteProperty().asObject());

        colonne_article.setCellValueFactory(data -> data.getValue().article_venduProperty());
        colonne_quantite.setCellValueFactory(data -> data.getValue().quantite_venduProperty().asObject());
        colonne_prix_unitaire.setCellValueFactory(data -> data.getValue().prix_venteProperty().asObject());

        facture_table_view.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                try {
                    // Charger les articles de la facture sélectionnée
                    ArrayList<FactureDTO> details = factureDAO.listerFactureParIdVente(newV.getIdVente());
                    details_tableview.setItems(FXCollections.observableArrayList(details));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    remise_facture_label.setText("REMISE : " + venteDAO.getRemiseVente(newV.getIdVente()));
                    montant_vente_label.setText("MONTANT VENTE (S) : " + venteDAO.getTotalVente(newV.getIdVente()));
                    montant_facture_label.setText("TOTAL FACTURE (S) : " + venteDAO.getTotalToutesVente());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });


    }

    @FXML
    private void afficher_tableau_de_bord(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Parent root = loader.load();
        Tableau_bordController ctrl = loader.getController();
        ctrl.setDAO_TableauBord(venteDAO, depenseDAO, approvisionnementDAO);
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_vente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_vente.fxml"));
        Parent root = loader.load();
        Nouvelle_venteController ctrl = loader.getController();
        ctrl.setArticleDAO_NouvelleVenteDAO(articleDAO);
        ctrl.setFactureDAO_NouvelleVente(factureDAO);
        ctrl.setVenteDAO_NouvelleVenteDAO(venteDAO);
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Parent root = loader.load();
        Historique_venteController ctrl = loader.getController();
        ctrl.setFactureDAO_HistoriqueVente(factureDAO);
        ctrl.setVenteDAO_HistoriqueVente(venteDAO);
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void approvisionnement(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/approvisionnement.fxml"));
        Parent root = loader.load();
        ApprovisionnementController ctrl = loader.getController();
        ctrl.setApprovisionnementDAO_Appro(approvisionnementDAO);
        ctrl.setArticleDAO_Appro(articleDAO);
        try { ctrl.chargerApprovisionnement(); } catch (SQLException ignored) {}
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void consulter_stock(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/stock_nouvelle_article.fxml"));
        Parent root = loader.load();
        Stock_nouvelle_articleController ctrl = loader.getController();
        ctrl.setTypeArticleDAO_Stock(typeArticleDAO);
        ctrl.setArticleDAO_Stock(articleDAO);
        try { ctrl.chargerArticle(); ctrl.chargerTypeArticle(); } catch (SQLException ignored) {}
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Parent root = loader.load();
        Historique_depenseController ctrl = loader.getController();
        ctrl.setDepenseDAO_HistoriqueDepense(depenseDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Parent root = loader.load();
        Nouvelle_depenseController ctrl = loader.getController();
        ctrl.setDepenseDAO_NouvelleDepense(depenseDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/entrees.fxml"));
        Parent root = loader.load();
        EntreesController ctrl = loader.getController();
        ctrl.setVenteDAO_Entrees(venteDAO);
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void sorties_caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/sorties.fxml"));
        Parent root = loader.load();
        SortiesController ctrl = loader.getController();
        ctrl.setApprovisionnementDAO(approvisionnementDAO);
        ctrl.setDepenseDAO_Sorties(depenseDAO);
        ctrl.setArticleDAO(articleDAO);
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/caisse.fxml"));
        Parent root = loader.load();
        CaisseController ctrl = loader.getController();
        ctrl.setDAO_Caisse(venteDAO, depenseDAO, approvisionnementDAO);
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Parent root = loader.load();
        UtilisateurController ctrl = loader.getController();
        ctrl.setUtilisateurDAO_Utilisateur(utilisateurDAO);
        try { ctrl.chargerUtilisateur(); } catch (SQLException ignored) {}
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/login.fxml"));
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void filtre_historique_facture(ActionEvent event) throws SQLException {
        boolean okDebut = ValidationEntree.validerDateObligatoire(date_debut_historique_facture);
        boolean okFin = ValidationEntree.validerDateObligatoire(date_fin_historique_facture);
        if (okDebut && okFin) {
            remise_facture_label.setText("");
            montant_facture_label.setText("");
            montant_vente_label.setText("");

            vente_observable_list.clear();
            vente_observable_list.setAll(venteDAO.filtrerVenteDate(date_debut_historique_facture.getValue(), date_fin_historique_facture.getValue()));

            Vente newV = facture_table_view.getSelectionModel().getSelectedItem();
            try {
                remise_facture_label.setText("REMISE : " + venteDAO.getRemiseVente(newV.getIdVente()));
                montant_vente_label.setText("MONTANT VENTE (S) : " + venteDAO.getTotalVente(newV.getIdVente()));
                montant_facture_label.setText("TOTAL FACTURE (S) : " + venteDAO.getTotalToutesVente());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            //appel alerte date erreur
        }
    }

    public void chargerVente() throws SQLException {
        if (venteDAO != null) {
            vente_observable_list.setAll(venteDAO.obtenirVentesBDD());
        }
    }
    
}
