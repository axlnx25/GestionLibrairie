/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import app_dao.*;
import app_helper.Session;
import app_helper.ValidationEntree;
import app_model.Type_Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author axlnx
 */
public class Type_articleController implements Initializable {

    @FXML
    private TableView<Type_Article> type_article_tableview;
    @FXML
    private TableColumn<Type_Article, Integer> colonne_identifiant;
    @FXML
    private TableColumn<Type_Article, String> colonne_type_article;
    @FXML
    private TextField type_article_textfield;

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

    public void setTypeArticleDAO_TypeArticle(TypeArticleDAO typeArticle) {
        this.typeArticleDAO = typeArticle;
    }
    ObservableList<Type_Article> type_articlesObservable = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        type_article_tableview.setItems(type_articlesObservable);

        colonne_identifiant.setCellValueFactory(data -> data.getValue().idTypeArticleProperty().asObject());
        colonne_type_article.setCellValueFactory(data -> data.getValue().typeArticleProperty());

        type_article_tableview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                type_article_textfield.setText(newValue.getTypeArticle());
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
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_vente(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_vente.fxml"));
        Parent root = loader.load();
        Nouvelle_venteController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        ctrl.loadCombo();
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Parent root = loader.load();
        Historique_venteController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void approvisionnement(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/approvisionnement.fxml"));
        Parent root = loader.load();
        ApprovisionnementController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerApprovisionnement(); } catch (SQLException ignored) {}
        ctrl.loadCombo();
        ctrl.loadColArticle();
        ctrl.chargerApprovisionnement();
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void consulter_stock(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/stock_nouvelle_article.fxml"));
        Parent root = loader.load();
        Stock_nouvelle_articleController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerArticle(); ctrl.chargerTypeArticle(); } catch (SQLException ignored) {}
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Parent root = loader.load();
        Historique_depenseController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Parent root = loader.load();
        Nouvelle_depenseController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/entrees.fxml"));
        Parent root = loader.load();
        EntreesController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        ctrl.loadEntrees();
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void soties_caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/sorties.fxml"));
        Parent root = loader.load();
        SortiesController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        ctrl.loadSorties();
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/caisse.fxml"));
        Parent root = loader.load();
        CaisseController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Parent root = loader.load();
        UtilisateurController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerUtilisateur(); } catch (SQLException ignored) {}
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/login.fxml"));
        Parent root = loader.load();
        LoginController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void ajouter_type(ActionEvent event) throws SQLException {
        boolean okNomType = ValidationEntree.validerTexteObligatoire(type_article_textfield);
        if (okNomType) {
            Type_Article t = new Type_Article(
                    type_article_textfield.getText(),
                    Session.getInstance().getIDUtilisateur()
            );
            typeArticleDAO.ajouterTypeArticleBDD(t);
            type_articlesObservable.add(t);
        } else {
            //appel alerte verifier les champ
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CHAMPS INVALIDES");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez verifier les informations saisies");
            alert.showAndWait();
        }
    }

    @FXML
    private void modifier_type(ActionEvent event) throws SQLException {
        Type_Article selected = type_article_tableview.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean okNomType = ValidationEntree.validerTexteObligatoire(type_article_textfield);
            if (okNomType) {
                selected.setTypeArticle(type_article_textfield.getText());

                typeArticleDAO.modifierTypeArticleBDD(selected);
            } else {
                //appel alerte verifier les champ
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("CHAMPS INVALIDES");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez verifier les informations saisies");
                alert.showAndWait();
            }

        } else {
            //appel alerte selection vide
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERREUR SELECTION");
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez rien selectionner selectionner.");
            alert.showAndWait();
        }
    }

    @FXML
    private void supprimer_type(ActionEvent event) throws SQLException {
        Type_Article selected = type_article_tableview.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean okNomType = ValidationEntree.validerTexteObligatoire(type_article_textfield);
            if (okNomType) {
                typeArticleDAO.supprimerTypeArticleBDD(selected);
                type_articlesObservable.remove(selected);
            } else {
                //appel alerte verifier les champ
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("CHAMPS INVALIDES");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez verifier les informations saisies");
                alert.showAndWait();
            }

        } else {
            //appel alerte selection vide
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERREUR SELECTION");
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez rien selectionner selectionner.");
            alert.showAndWait();
        }
    }

    public void chargerTypeArticle() throws SQLException {
        if (typeArticleDAO != null) {
            type_articlesObservable.setAll(typeArticleDAO.obtenirTypeArticleBDD());
        }
    }
    
}
