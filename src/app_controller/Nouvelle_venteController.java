/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import app_dao.*;
import app_helper.Session;
import app_helper.ValidationEntree;
import app_model.Article;
import app_model.Facture;
import app_model.LigneFacture;
import app_model.Vente;
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
        if (articleDAO != null) {
            try { loadCombo(); } catch (SQLException e) { e.printStackTrace(); }
        }
        colonne_article.setCellValueFactory(data -> data.getValue().designationArticleProperty());
        colonne_quantite.setCellValueFactory(data -> data.getValue().quantiteProperty().asObject());
        colonne_prix_unitaire.setCellValueFactory(data -> data.getValue().prixUnitaireProperty().asObject());

        nouvelle_vente_tableview.setItems(lignesFactureObservable);
        remise_text_field.setText("0");
    }

    @FXML
    private void afficher_tableau_de_bord(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Parent root = loader.load();
        Tableau_bordController ctrl = loader.getController();
        ctrl.setDAO_TableauBord(venteDAO, depenseDAO, approvisionnementDAO);
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
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
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Parent root = loader.load();
        Historique_venteController ctrl = loader.getController();
        ctrl.setFactureDAO_HistoriqueVente(factureDAO);
        ctrl.setVenteDAO_HistoriqueVente(venteDAO);
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
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
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
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
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Parent root = loader.load();
        Historique_depenseController ctrl = loader.getController();
        ctrl.setDepenseDAO_HistoriqueDepense(depenseDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Parent root = loader.load();
        Nouvelle_depenseController ctrl = loader.getController();
        ctrl.setDepenseDAO_NouvelleDepense(depenseDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/entrees.fxml"));
        Parent root = loader.load();
        EntreesController ctrl = loader.getController();
        ctrl.setVenteDAO_Entrees(venteDAO);
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
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
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/caisse.fxml"));
        Parent root = loader.load();
        CaisseController ctrl = loader.getController();
        ctrl.setDAO_Caisse(venteDAO, depenseDAO, approvisionnementDAO);
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Parent root = loader.load();
        UtilisateurController ctrl = loader.getController();
        ctrl.setUtilisateurDAO_Utilisateur(utilisateurDAO);
        try { ctrl.chargerUtilisateur(); } catch (SQLException ignored) {}
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/login.fxml"));
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
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
