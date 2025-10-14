/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
        date_vente.setValue(LocalDate.now());
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
    private void nouvelle_vente(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_vente.fxml"));
        Parent root = loader.load();
        Nouvelle_venteController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        ctrl.loadCombo();
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Parent root = loader.load();
        Historique_venteController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
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
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void consulter_stock(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/stock_nouvelle_article.fxml"));
        Parent root = loader.load();
        Stock_nouvelle_articleController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerArticle(); ctrl.chargerTypeArticle(); } catch (SQLException ignored) {}
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Parent root = loader.load();
        Historique_depenseController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Parent root = loader.load();
        Nouvelle_depenseController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/entrees.fxml"));
        Parent root = loader.load();
        EntreesController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        ctrl.loadEntrees();
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void sorties_caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/sorties.fxml"));
        Parent root = loader.load();
        SortiesController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        ctrl.loadSorties();
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/caisse.fxml"));
        Parent root = loader.load();
        CaisseController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Parent root = loader.load();
        UtilisateurController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerUtilisateur(); } catch (SQLException ignored) {}
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/login.fxml"));
        Parent root = loader.load();
        LoginController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) remise_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void ajouter_article(ActionEvent event) {
        Article selectedArticle = article_vente_combobox.getSelectionModel().getSelectedItem();

        boolean okQuantite = ValidationEntree.validerNombre(quantite_vente_textfield);

        if (selectedArticle == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Article manquant");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un article avant d’ajouter.");
            alert.showAndWait();
            return;
        }

        if (!okQuantite) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Quantité invalide");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer une quantité numérique valide.");
            alert.showAndWait();
            return;
        }

        int quantiteDemandee = Integer.parseInt(quantite_vente_textfield.getText());

        if (quantiteDemandee <= 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Quantité invalide");
            alert.setHeaderText(null);
            alert.setContentText("La quantité doit être supérieure à zéro.");
            alert.showAndWait();
            return;
        }

        if (quantiteDemandee > selectedArticle.getQuantiteArticle()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Stock insuffisant");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Stock insuffisant pour cet article.\n" +
                            "Disponible : " + selectedArticle.getQuantiteArticle() +
                            " — Demandé : " + quantiteDemandee
            );
            alert.showAndWait();
            return;
        }

        // Si tout est bon, on continue :
        prix_unitaire_label.setText("PRIX UNITAIRE : " + selectedArticle.getPrixVenteArticle());
        LigneFacture ligne = new LigneFacture(selectedArticle, quantiteDemandee);
        lignesFactureObservable.add(ligne);
        recalculerMontantTotal();

        // On réinitialise les champs
        article_vente_combobox.setValue(null);
        quantite_vente_textfield.clear();
        prix_unitaire_label.setText("");
    }


    @FXML
    private void valider_facture(ActionEvent event) throws SQLException {
        boolean okRmise = ValidationEntree.validerNombre(remise_text_field);
        boolean okDate = ValidationEntree.validerDateObligatoire(date_vente);
        if (okRmise && okDate) {
            Vente v = new Vente(
                    0,
                    date_vente.getValue(),
                    Integer.parseInt(remise_text_field.getText()),
                    Session.getInstance().getIDUtilisateur()
            );

            // 1️On ajoute la vente
            venteDAO.ajouterVenteBDD(v);

            // 2️On ajoute chaque ligne de facture et on met à jour les stocks
            for (LigneFacture ligneFacture : lignesFactureObservable) {
                factureDAO.ajouterFactureBDD(
                        new Facture(v.getIdVente(), ligneFacture.getArticle().getIdArticle(), ligneFacture.getQuantite())
                );

                //  On diminue le stock de l’article correspondant
                articleDAO.diminuerQuantiteArticle(
                        ligneFacture.getArticle().getIdArticle(),
                        ligneFacture.getQuantite()
                );
            }

            // 3️On nettoie les champs
            remise_text_field.setText("0");
            date_vente.setValue(null);
            lignesFactureObservable.clear();
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
