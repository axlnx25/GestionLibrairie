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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import app_dao.*;
import app_helper.Session;
import app_helper.ValidationEntree;
import app_model.Approvisionnement;
import app_model.Article;
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
public class ApprovisionnementController implements Initializable {

    @FXML
    private TableView<Approvisionnement> approvisionnement_tableview;
    @FXML
    private TableColumn<Approvisionnement, String> colonne_fournisseur;
    @FXML
    private TableColumn<Approvisionnement, String> colonne_article;
    @FXML
    private TableColumn<Approvisionnement, Integer> colonne_quantite;
    @FXML
    private TableColumn<Approvisionnement, Integer> colonne_montant;
    @FXML
    private TableColumn<Approvisionnement, LocalDate> colonne_date;
    @FXML
    private ComboBox<String> fournisseur_combo_box;
    @FXML
    private ComboBox<Article> article_combo_box;
    @FXML
    private TextField quantite_text_field;
    @FXML
    private TextField montant_text_field;
    @FXML
    private DatePicker date_text_field;

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

    public void setApprovisionnementDAO_Appro(ApprovisionnementDAO approvisionnement) {
        this.approvisionnementDAO = approvisionnement;
    }
    public void setArticleDAO_Appro(ArticleDAO article) {
        this.articleDAO = article;
    }

    ObservableList<Approvisionnement> approvisionnementObservableList = FXCollections.observableArrayList();
    ObservableList<String> articleObservableList = FXCollections.observableArrayList();

    private Map<Integer, StringProperty> articleMap;
    public void loadColArticle() throws SQLException {
        List<Article> artlist = articleDAO.obtenirArticleBDD();
        articleMap = artlist.stream().collect(Collectors.toMap(Article::getIdArticle, Article::nomArticleProperty));
        colonne_article.setCellValueFactory(data -> {
            int idArt = data.getValue().getNumeroArticle();
            return articleMap.get(idArt);
        });
    }

    public void loadCombo() throws SQLException {
        List<Article> artlist = articleDAO.obtenirArticleBDD();
        article_combo_box.getItems().setAll(artlist);
        article_combo_box.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Article item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomArticle());
            }
        });

        article_combo_box.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Article item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomArticle());
            }
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colonne_fournisseur.setCellValueFactory(data -> data.getValue().stockOrigineProperty());
        if (articleDAO != null) {
            try {
                loadColArticle();
                loadCombo();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        colonne_quantite.setCellValueFactory(data -> data.getValue().quantiteApprovisionneProperty().asObject());
        colonne_montant.setCellValueFactory(data -> data.getValue().montantApprovisionnementProperty().asObject());
        colonne_date.setCellValueFactory(data -> data.getValue().dateApprovisionnementProperty());

        approvisionnement_tableview.setItems(approvisionnementObservableList);
        ObservableList<String> fournisseurObservableList = FXCollections.observableArrayList(
                "EAS", "Autres"
        );
        fournisseur_combo_box.setItems(fournisseurObservableList);

        approvisionnement_tableview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fournisseur_combo_box.setValue(newValue.getStockOrigine());
                quantite_text_field.setText(String.valueOf(newValue.getQuantiteApprovisionne()));
                montant_text_field.setText(String.valueOf(newValue.getMontantApprovisionne()));
                date_text_field.setValue(newValue.getDateApprovisionnement());

                // retrouver l'article par son id
                Article selectedArticle = article_combo_box.getItems().stream()
                        .filter(a -> a.getIdArticle() == newValue.getNumeroArticle())
                        .findFirst()
                        .orElse(null);

                article_combo_box.setValue(selectedArticle);
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
        Stage stage = (Stage) montant_text_field.getScene().getWindow();
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
        Stage stage = (Stage) montant_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Parent root = loader.load();
        Historique_venteController ctrl = loader.getController();
        ctrl.setFactureDAO_HistoriqueVente(factureDAO);
        ctrl.setVenteDAO_HistoriqueVente(venteDAO);
        Stage stage = (Stage) montant_text_field.getScene().getWindow();
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
        Stage stage = (Stage) montant_text_field.getScene().getWindow();
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
        Stage stage = (Stage) montant_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Parent root = loader.load();
        Historique_depenseController ctrl = loader.getController();
        ctrl.setDepenseDAO_HistoriqueDepense(depenseDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) montant_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Parent root = loader.load();
        Nouvelle_depenseController ctrl = loader.getController();
        ctrl.setDepenseDAO_NouvelleDepense(depenseDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) montant_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/entrees.fxml"));
        Parent root = loader.load();
        EntreesController ctrl = loader.getController();
        ctrl.setVenteDAO_Entrees(venteDAO);
        Stage stage = (Stage) montant_text_field.getScene().getWindow();
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
        Stage stage = (Stage) montant_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/caisse.fxml"));
        Parent root = loader.load();
        CaisseController ctrl = loader.getController();
        ctrl.setDAO_Caisse(venteDAO, depenseDAO, approvisionnementDAO);
        Stage stage = (Stage) montant_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void menu_utilisateur(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Parent root = loader.load();
        UtilisateurController ctrl = loader.getController();
        ctrl.setUtilisateurDAO_Utilisateur(utilisateurDAO);
        try { ctrl.chargerUtilisateur(); } catch (SQLException ignored) {}
        Stage stage = (Stage) montant_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/login.fxml"));
        Stage stage = (Stage) montant_text_field.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void ajouter_approvisionnement(ActionEvent event) throws SQLException {
        boolean okFournisseur = ValidationEntree.validerCombobox(fournisseur_combo_box);
        boolean okQuantite = ValidationEntree.validerNombre(quantite_text_field);
        boolean okMontant = ValidationEntree.validerNombre(montant_text_field);
        boolean okDate = ValidationEntree.validerDateObligatoire(date_text_field);
        if (okDate && okMontant && okFournisseur && okQuantite) {
            if (article_combo_box.getValue() != null) {
                Approvisionnement a = new Approvisionnement(
                        fournisseur_combo_box.getValue(),
                        Integer.parseInt(quantite_text_field.getText()),
                        date_text_field.getValue(),
                        Integer.parseInt(montant_text_field.getText()),
                        article_combo_box.getValue().getIdArticle(),
                        Session.getInstance().getIDUtilisateur()
                );
                approvisionnementDAO.ajouterApprovisionnementBDD(a);
                approvisionnementObservableList.add(a);

                fournisseur_combo_box.setValue(null);
                quantite_text_field.clear();
                montant_text_field.clear();
                date_text_field.setValue(null);
                article_combo_box.setValue(null);
            } else {
                //appel alert probleme date
            }
        } else {
            //appel alert verifier les valeurs des champs
        }
    }

    @FXML
    private void modifier_approvisionnement(ActionEvent event) throws SQLException {
        Approvisionnement selected = approvisionnement_tableview.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean okFournisseur = ValidationEntree.validerCombobox(fournisseur_combo_box);
            boolean okQuantite = ValidationEntree.validerNombre(quantite_text_field);
            boolean okMontant = ValidationEntree.validerNombre(montant_text_field);
            boolean okDate = ValidationEntree.validerDateObligatoire(date_text_field);
            if (okDate && okMontant && okFournisseur && okQuantite) {
                if (article_combo_box.getValue() != null) {
                    selected.setStockOrigine(fournisseur_combo_box.getValue());
                    selected.setDateApprovisionnement(date_text_field.getValue());
                    selected.setMontantApprovisionne(Integer.parseInt(montant_text_field.getText()));
                    selected.setQuantiteApprovisionne(Integer.parseInt(quantite_text_field.getText()));
                    selected.setNumeroArticle(article_combo_box.getValue().getIdArticle());
                    selected.setCodeUtilisateur(Session.getInstance().getIDUtilisateur());

                    approvisionnementDAO.modifierApprovisionnementBDD(selected);

                    fournisseur_combo_box.setValue(null);
                    quantite_text_field.clear();
                    montant_text_field.clear();
                    date_text_field.setValue(null);
                    article_combo_box.setValue(null);
                } else {
                    //appel alert probleme date
                }
            } else {
                //appel alert verifier les valeurs des champs
            }
        } else {
            //appel alert non selectionner
        }
    }

    @FXML
    private void supprimer_approvisionnement(ActionEvent event) throws SQLException {
        Approvisionnement selected = approvisionnement_tableview.getSelectionModel().getSelectedItem();
        if (selected != null) {
            approvisionnementObservableList.remove(selected);
            approvisionnementDAO.supprimerApprovisionnementBDD(selected);

            fournisseur_combo_box.setValue(null);
            quantite_text_field.clear();
            montant_text_field.clear();
            date_text_field.setValue(null);
            article_combo_box.setValue(null);
        } else {
            //appel alert non selectionner
        }

    }

    public void chargerApprovisionnement() throws SQLException {
        if (approvisionnementDAO != null) {
            approvisionnementObservableList.setAll(approvisionnementDAO.obtenirApprovisionnementsBDD());
        }
    }
    
}
