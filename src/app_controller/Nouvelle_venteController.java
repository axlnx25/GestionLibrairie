/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import app_dao.ArticleDAO;
import app_dao.FactureDAO;
import app_dao.VenteDAO;
import app_helper.Session;
import app_helper.ValidationEntree;
import app_model.Article;
import app_model.Facture;
import app_model.LigneFacture;
import app_model.Vente;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
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
public class Nouvelle_venteController implements Initializable {

    @FXML
    private ComboBox<Article> article_vente_combobox;
    @FXML
    private Label prix_unitaire_label;
    @FXML
    private TableView<LigneFacture> nouvelle_vente_tableview;
    @FXML
    private TableColumn<LigneFacture, String> colonne_article;
    @FXML
    private TableColumn<LigneFacture, Integer> colonne_quantite;
    @FXML
    private TableColumn<LigneFacture, Integer> colonne_prix_unitaire;
    @FXML
    private Label montant_facture_label;
    @FXML
    private TextField quantite_vente_textfield;
    @FXML
    private TextField remise_text_field;
    @FXML
    private DatePicker date_vente;

    /**
     * Initializes the controller class.
     */

    private ArticleDAO articleDAO;
    private VenteDAO venteDAO;
    private FactureDAO factureDAO;

    public void setArticleDAO_NouvelleVenteDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }
    public void setVenteDAO_NouvelleVenteDAO(VenteDAO venteDAO) {
        this.venteDAO = venteDAO;
    }
    public void setFactureDAO_NouvelleVente(FactureDAO factureDAO) {
        this.factureDAO = factureDAO;
    }

    public void loadCombo() throws SQLException {
        List<Article> artlist = articleDAO.obtenirArticleBDD();
        article_vente_combobox.getItems().setAll(artlist);
        article_vente_combobox.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Article item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomArticle());
            }
        });

        article_vente_combobox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Article item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomArticle());
            }
        });

    }
    private ObservableList<LigneFacture> lignesFactureObservable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            loadCombo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        colonne_article.setCellValueFactory(data -> data.getValue().designationArticleProperty());
        colonne_quantite.setCellValueFactory(data -> data.getValue().quantiteProperty().asObject());
        colonne_prix_unitaire.setCellValueFactory(data -> data.getValue().prixUnitaireProperty().asObject());

        nouvelle_vente_tableview.setItems(lignesFactureObservable);
        remise_text_field.setText("0");
    }

    @FXML
    private void afficher_tableau_de_bord(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Stage stage = (Stage) quantite_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_vente.fxml"));
        Stage stage = (Stage) quantite_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Stage stage = (Stage) quantite_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void approvisionnement(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/approvisionnement.fxml"));
        Stage stage = (Stage) quantite_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void consulter_stock(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/stock_nouvelle_article.fxml"));
        Stage stage = (Stage) quantite_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Stage stage = (Stage) quantite_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Stage stage = (Stage) quantite_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/entrees.fxml"));
        Stage stage = (Stage) quantite_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void sorties_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/sorties.fxml"));
        Stage stage = (Stage) quantite_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/caisse.fxml"));
        Stage stage = (Stage) quantite_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Stage stage = (Stage) quantite_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/login.fxml"));
        Stage stage = (Stage) quantite_vente_textfield.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void ajouter_article(ActionEvent event) {
        Article selectedArticle = article_vente_combobox.getSelectionModel().getSelectedItem();
        boolean okQuantite = ValidationEntree.validerNombre(quantite_vente_textfield);
        if ((selectedArticle != null) && okQuantite) {
            prix_unitaire_label.setText("PRIX UNITAIRE : " + selectedArticle.getPrixVenteArticle());
            LigneFacture ligne = new LigneFacture(selectedArticle, Integer.parseInt(quantite_vente_textfield.getText()));

            ligne.setQuantite(Integer.parseInt(quantite_vente_textfield.getText()));
            lignesFactureObservable.add(ligne);

            recalculerMontantTotal();
        }

        article_vente_combobox.setValue(null);
        quantite_vente_textfield.clear();
        prix_unitaire_label.setText("");
    }

    @FXML
    private void valider_facture(ActionEvent event) throws SQLException {
        boolean okRmise = ValidationEntree.validerNombre(remise_text_field);
        boolean okDate = ValidationEntree.validerDateObligatoire(date_vente);
        if (okRmise &&  okDate) {
            Vente v = new Vente(
                    0,
                    date_vente.getValue(),
                    Integer.parseInt(remise_text_field.getText()),
                    Session.getInstance().getIDUtilisateur()
            );
            venteDAO.ajouterVenteBDD(v);

            for (LigneFacture ligneFacture: lignesFactureObservable) {
                factureDAO.ajouterFactureBDD(
                        new Facture(v.getIdVente(), ligneFacture.getArticle().getIdArticle(), ligneFacture.getQuantite())
                );
            }
            remise_text_field.setText("0");
            date_vente.setValue(null);
        }
    }

    @FXML
    private void annuler_vente(ActionEvent event) {
        LigneFacture selected = nouvelle_vente_tableview.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lignesFactureObservable.remove(selected);
            recalculerMontantTotal();
        }
    }

    private void recalculerMontantTotal() {
        double total = lignesFactureObservable.stream()
                .mapToInt(l -> l.prixUnitaireProperty().get() * l.quantiteProperty().get())
                .sum() - Integer.parseInt(remise_text_field.getText());
        montant_facture_label.setText("MONTANT : " + total);
    }


}
