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

    public void setArticleDAO_Stock(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }
    public void setTypeArticleDAO_Stock(TypeArticleDAO typeArticleDAO) {
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

        stock_tableview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                article_textfield.setText(newValue.getNomArticle());
                type_article_combobox.setValue(newValue.getTypeArticle());
                prix_vente_textfield.setText(String.valueOf(newValue.getPrixVenteArticle()));
                quantite_textfield.setText(String.valueOf(newValue.getQuantiteArticle()));
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
        Stage stage = (Stage) prix_vente_textfield.getScene().getWindow();
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
        Stage stage = (Stage) prix_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Parent root = loader.load();
        Historique_venteController ctrl = loader.getController();
        ctrl.setFactureDAO_HistoriqueVente(factureDAO);
        ctrl.setVenteDAO_HistoriqueVente(venteDAO);
        Stage stage = (Stage) prix_vente_textfield.getScene().getWindow();
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
        Stage stage = (Stage) prix_vente_textfield.getScene().getWindow();
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
        Stage stage = (Stage) prix_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Parent root = loader.load();
        Historique_depenseController ctrl = loader.getController();
        ctrl.setDepenseDAO_HistoriqueDepense(depenseDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) prix_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Parent root = loader.load();
        Nouvelle_depenseController ctrl = loader.getController();
        ctrl.setDepenseDAO_NouvelleDepense(depenseDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) prix_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/entrees.fxml"));
        Parent root = loader.load();
        EntreesController ctrl = loader.getController();
        ctrl.setVenteDAO_Entrees(venteDAO);
        Stage stage = (Stage) prix_vente_textfield.getScene().getWindow();
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
        Stage stage = (Stage) prix_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/caisse.fxml"));
        Parent root = loader.load();
        CaisseController ctrl = loader.getController();
        ctrl.setDAO_Caisse(venteDAO, depenseDAO, approvisionnementDAO);
        Stage stage = (Stage) prix_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Parent root = loader.load();
        UtilisateurController ctrl = loader.getController();
        ctrl.setUtilisateurDAO_Utilisateur(utilisateurDAO);
        try { ctrl.chargerUtilisateur(); } catch (SQLException ignored) {}
        Stage stage = (Stage) prix_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/login.fxml"));
        Stage stage = (Stage) prix_vente_textfield.getScene().getWindow();
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
            //appel alerte verifier champ
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
