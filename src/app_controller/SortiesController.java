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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import app_dao.*;
import app_model.Approvisionnement;
import app_model.Article;
import app_model.Depense;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author axlnx
 */
public class SortiesController implements Initializable {

    @FXML
    private TableView<Depense> depnse_tableview;
    @FXML
    private TableColumn<Depense, String> col_depense_motif;
    @FXML
    private TableColumn<Depense, Integer> col_depense_montant;
    @FXML
    private TableColumn<Depense, LocalDate> col_depense_date;
    @FXML
    private Label valeur_depense_label;
    @FXML
    private TableView<Approvisionnement> appprovisionnement_tableview;
    @FXML
    private TableColumn<Approvisionnement, String> col_app_fournisseur;
    @FXML
    private TableColumn<Approvisionnement, String> col_app_articlenom;
    @FXML
    private TableColumn<Approvisionnement, Integer> col_app_quantite_appro;
    @FXML
    private TableColumn<Approvisionnement, Integer> col_app_montant_appro;
    @FXML
    private Label valeur_approvisionnement_label;
    @FXML
    private Label total_sortie_label;

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

    public void setDepenseDAO_Sorties(DepenseDAO depenseDAO) {
        this.depenseDAO = depenseDAO;
    }
    public void setApprovisionnementDAO(ApprovisionnementDAO approvisionnementDAO) {
        this.approvisionnementDAO = approvisionnementDAO;
    }
    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Config colonnes
        col_depense_motif.setCellValueFactory(data -> data.getValue().designationDepenseProperty());
        col_depense_montant.setCellValueFactory(data -> data.getValue().montantDepenseProperty().asObject());
        col_depense_date.setCellValueFactory(data -> data.getValue().dateDepenseProperty());
        col_app_fournisseur.setCellValueFactory(data -> data.getValue().stockOrigineProperty());
        col_app_quantite_appro.setCellValueFactory(data -> data.getValue().quantiteApprovisionneProperty().asObject());
        col_app_montant_appro.setCellValueFactory(data -> data.getValue().montantApprovisionnementProperty().asObject());

        if (depenseDAO != null && approvisionnementDAO != null && articleDAO != null) {
            try {
                ArrayList<Depense> depenses = depenseDAO.listerDepenseBDD();
                depnse_tableview.setItems(FXCollections.observableArrayList(depenses));
                int totalDepenses = depenseDAO.getTotalDepense();
                valeur_depense_label.setText(totalDepenses + " FCFA");

                ArrayList<Article> articles = articleDAO.obtenirArticleBDD();
                Map<Integer, StringProperty> articleMap = articles.stream()
                        .collect(Collectors.toMap(Article::getIdArticle, Article::nomArticleProperty));
                col_app_articlenom.setCellValueFactory(data -> articleMap.get(data.getValue().getNumeroArticle()));

                ArrayList<Approvisionnement> appros = approvisionnementDAO.obtenirApprovisionnementsBDD();
                appprovisionnement_tableview.setItems(FXCollections.observableArrayList(appros));
                int totalAppros = approvisionnementDAO.getTotalApprovisionnement();
                valeur_approvisionnement_label.setText(totalAppros + " FCFA");

                total_sortie_label.setText((totalDepenses + totalAppros) + " FCFA");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void afficher_tableau_de_bord(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Parent root = loader.load();
        Tableau_bordController ctrl = loader.getController();
        ctrl.setDAO_TableauBord(venteDAO, depenseDAO, approvisionnementDAO);
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
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
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Parent root = loader.load();
        Historique_venteController ctrl = loader.getController();
        ctrl.setFactureDAO_HistoriqueVente(factureDAO);
        ctrl.setVenteDAO_HistoriqueVente(venteDAO);
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
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
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
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
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Parent root = loader.load();
        Historique_depenseController ctrl = loader.getController();
        ctrl.setDepenseDAO_HistoriqueDepense(depenseDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Parent root = loader.load();
        Nouvelle_depenseController ctrl = loader.getController();
        ctrl.setDepenseDAO_NouvelleDepense(depenseDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/entrees.fxml"));
        Parent root = loader.load();
        EntreesController ctrl = loader.getController();
        ctrl.setVenteDAO_Entrees(venteDAO);
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
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
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/caisse.fxml"));
        Parent root = loader.load();
        CaisseController ctrl = loader.getController();
        ctrl.setDAO_Caisse(venteDAO, depenseDAO, approvisionnementDAO);
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Parent root = loader.load();
        UtilisateurController ctrl = loader.getController();
        ctrl.setUtilisateurDAO_Utilisateur(utilisateurDAO);
        try { ctrl.chargerUtilisateur(); } catch (SQLException ignored) {}
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/login.fxml"));
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    
}
