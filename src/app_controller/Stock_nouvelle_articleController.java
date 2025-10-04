/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import app_dao.ArticleDAO;
import app_dao.TypeArticleDAO;
import app_dao.UtilisateurDAO;
import app_helper.Session;
import app_helper.ValidationEntree;
import app_model.Article;
import app_model.Type_Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author axlnx
 */
public class Stock_nouvelle_articleController implements Initializable {

    @FXML
    private ComboBox<String> filtre_type_article_combobox;
    @FXML
    private TableView<Article> stock_tableview;
    @FXML
    private TableColumn<Article, String> colonne_article;
    @FXML
    private TableColumn<Article, Integer> colonne_quantite;
    @FXML
    private TableColumn<Article, Integer> colonne_prix_vente;
    @FXML
    private Label valeur_stock_label;
    @FXML
    private TextField article_textfield;
    @FXML
    private TextField quantite_textfield;
    @FXML
    private TextField prix_vente_textfield;
    @FXML
    private ComboBox<String> type_article_combobox;

    /**
     * Initializes the controller class.
     */

    private ArticleDAO articleDAO;
    private TypeArticleDAO  typeArticleDAO;

    public void setArticleDAO_Stock(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }
    public void setTypeArticleDAO(TypeArticleDAO typeArticleDAO) {
        this.typeArticleDAO = typeArticleDAO;
    }


    ObservableList<Article> articleObservableList = FXCollections.observableArrayList();
    ObservableList<String> type_articleObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stock_tableview.setItems(articleObservableList);
        type_article_combobox.setItems(type_articleObservableList);
        filtre_type_article_combobox.setItems(type_articleObservableList);

        colonne_article.setCellValueFactory(data -> data.getValue().nomArticleProperty());
        colonne_quantite.setCellValueFactory(data -> data.getValue().quantiteArticleProperty().asObject());
        colonne_prix_vente.setCellValueFactory(data -> data.getValue().prixVenteArticleProperty().asObject());

    }    

    @FXML
    private void afficher_tableau_de_bord(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Stage stage = (Stage) type_article_combobox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_vente.fxml"));
        Stage stage = (Stage) type_article_combobox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Stage stage = (Stage) type_article_combobox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void approvisionnement(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/approvisionnement.fxml"));
        Stage stage = (Stage) type_article_combobox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void consulter_stock(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/consulter_stock.fxml"));
        Stage stage = (Stage) type_article_combobox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Stage stage = (Stage) type_article_combobox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Stage stage = (Stage) type_article_combobox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/entrees_caisse.fxml"));
        Stage stage = (Stage) type_article_combobox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void sorties_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/sorties_caisse.fxml"));
        Stage stage = (Stage) type_article_combobox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/caisse.fxml"));
        Stage stage = (Stage) type_article_combobox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Stage stage = (Stage) type_article_combobox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/deconnexion.fxml"));
        Stage stage = (Stage) type_article_combobox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void bouton_filtre(ActionEvent event) throws SQLException {
        boolean okFiltre = ValidationEntree.validerCombobox(filtre_type_article_combobox);
        if (okFiltre) {
            for (Article article : stock_tableview.getItems()) {
                if (!(article.getTypeArticle().equals(filtre_type_article_combobox.getValue()))) {
                    articleObservableList.remove(article);
                }
            }
            valeur_stock_label.setText(valeurStock(stock_tableview.getItems()));
        } else  {
            //appelle alerte combo vide
        }
    }

    @FXML
    private void ajouter_type_article(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/app_fxml/type_article.fxml"));
        Stage stage = (Stage) type_article_combobox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void ajouter_article(ActionEvent event) throws SQLException {
        boolean okArticle = ValidationEntree.validerTexteObligatoire(article_textfield);
        boolean okQuantite = ValidationEntree.validerNombre(quantite_textfield);
        boolean okPrixVente = ValidationEntree.validerNombre(prix_vente_textfield);
        boolean okType = ValidationEntree.validerCombobox(type_article_combobox);

        if (okArticle && okQuantite && okPrixVente && okType) {
            Article a = new Article(
                    Integer.parseInt(quantite_textfield.getText()),
                    Integer.parseInt(prix_vente_textfield.getText()),
                    article_textfield.getText(),
                    type_article_combobox.getValue(),
                    Session.getInstance().getIDUtilisateur()
            );
            articleObservableList.add(a);
            articleDAO.ajouterArticleBDD(a);

            article_textfield.clear();
            quantite_textfield.clear();
            prix_vente_textfield.clear();
            type_article_combobox.setValue(null);

            valeur_stock_label.setText(valeurStock(stock_tableview.getItems()));
        } else {
            //appel alerte
        }
    }

    @FXML
    private void modifier_article(ActionEvent event) throws SQLException {
        Article selection =  stock_tableview.getSelectionModel().getSelectedItem();
        if (selection != null) {
            boolean okArticle = ValidationEntree.validerTexteObligatoire(article_textfield);
            boolean okQuantite = ValidationEntree.validerNombre(quantite_textfield);
            boolean okPrixVente = ValidationEntree.validerNombre(prix_vente_textfield);
            boolean okType = ValidationEntree.validerCombobox(type_article_combobox);

            if (okArticle && okQuantite && okPrixVente && okType) {
                selection.setNomArticle(article_textfield.getText());
                selection.setQuantiteArticle(Integer.parseInt(quantite_textfield.getText()));
                selection.setPrixVenteArticle(Integer.parseInt(prix_vente_textfield.getText()));
                selection.setTypeArticle(type_article_combobox.getValue());

                articleDAO.modifierArticleBDD(selection);

                article_textfield.clear();
                quantite_textfield.clear();
                prix_vente_textfield.clear();
                type_article_combobox.setValue(null);

                valeur_stock_label.setText(valeurStock(stock_tableview.getItems()));
            } else {
                //appeler alerte verifier les champs
            }
        } else {
            //appeler alerte selection vide
        }
    }

    @FXML
    private void supprimer_article(ActionEvent event) throws SQLException {
        Article selection =  stock_tableview.getSelectionModel().getSelectedItem();
        if (selection != null) {
            articleObservableList.remove(selection);
            articleDAO.supprimerArticleBDD(selection);

            article_textfield.clear();
            quantite_textfield.clear();
            prix_vente_textfield.clear();
            type_article_combobox.setValue(null);

            valeur_stock_label.setText(valeurStock(articleObservableList));
        } else {
            //appel alerte selection vide
        }
    }

    public void chargerArticle() throws SQLException {
        if (articleDAO != null) {
            articleObservableList.setAll(articleDAO.obtenirArticleBDD());
        }
    }

    public void chargerTypeArticle() throws SQLException {
        if (typeArticleDAO != null) {
            for (Type_Article t : typeArticleDAO.obtenirTypeArticleBDD()) {
                type_articleObservableList.add(t.getTypeArticle());
            }
        }
    }

    public String valeurStock(ObservableList<Article> articleObservableList) throws SQLException {
        int somme = 0;
        for (Article article : articleObservableList) {
            somme += article.getPrixVenteArticle();
        }
        return String.valueOf(somme);
    }
    
}
