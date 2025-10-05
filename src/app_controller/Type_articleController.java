/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import app_dao.TypeArticleDAO;
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

    private TypeArticleDAO typeArticleDAO;
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
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_vente.fxml"));
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void approvisionnement(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/approvisionnement.fxml"));
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void consulter_stock(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/stock_nouvelle_article.fxml"));
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/entrees.fxml"));
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void soties_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/sorties.fxml"));
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/caisse.fxml"));
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Stage stage = (Stage) type_article_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/login.fxml"));
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
            }

        } else {
            //appel alerte selection vide
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
            }

        } else {
            //appel alerte selection vide
        }
    }

    public void chargerTypeArticle() throws SQLException {
        if (typeArticleDAO != null) {
            type_articlesObservable.setAll(typeArticleDAO.obtenirTypeArticleBDD());
        }
    }
    
}
