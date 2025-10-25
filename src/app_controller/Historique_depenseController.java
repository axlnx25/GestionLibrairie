/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import app_dao.*;
import app_helper.GestionAcces;
import app_helper.ValidationEntree;
import app_model.Depense;
import javafx.application.Platform;
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
public class Historique_depenseController implements Initializable {

    @FXML
    private DatePicker date_debut_historique_depense;
    @FXML
    private DatePicker date_fin_historique_depense;
    @FXML
    private TableView<Depense> historique_depense_table_view;
    @FXML
    private TableColumn<Depense, String> colonne_motif;
    @FXML
    private TableColumn<Depense, Integer> colonne_montant;
    @FXML
    private TableColumn<Depense, LocalDate> colonne_date;
    @FXML
    private Label valeur_depense_label;

    @FXML private  TitledPane tpUser;
    @FXML private  TitledPane tpTresor;
    @FXML private  TitledPane tpArticle;

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

    public void setDepenseDAO_HistoriqueDepense(DepenseDAO depenseDAO) {
        this.depenseDAO = depenseDAO;
    }
    ObservableList<Depense> depenseObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        GestionAcces.restrictForNonAdmin(tpUser, tpTresor, tpArticle);

        historique_depense_table_view.setItems(depenseObservableList);

        colonne_motif.setCellValueFactory(data -> data.getValue().designationDepenseProperty());
        colonne_montant.setCellValueFactory(data -> data.getValue().montantDepenseProperty().asObject());
        colonne_date.setCellValueFactory(data -> data.getValue().dateDepenseProperty());

        if (depenseDAO != null) {
            try {
                valeur_depense_label.setText("TOTAL DEPENSE : " + depenseDAO.getTotalDepense());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //  Définir le filtre par défaut du jour
        Platform.runLater(() -> {
            try {
                date_debut_historique_depense.setValue(LocalDate.now());
                date_fin_historique_depense.setValue(LocalDate.now());
                filtre_historique_depense_par_defaut();
            } catch (SQLException e) {
                e.printStackTrace();
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
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_vente(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_vente.fxml"));
        Parent root = loader.load();
        Nouvelle_venteController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        ctrl.loadCombo();
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Parent root = loader.load();
        Historique_venteController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
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
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void consulter_stock(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/stock_nouvelle_article.fxml"));
        Parent root = loader.load();
        Stock_nouvelle_articleController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerArticle(); ctrl.chargerTypeArticle(); } catch (SQLException ignored) {}
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Parent root = loader.load();
        Historique_depenseController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Parent root = loader.load();
        Nouvelle_depenseController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/entrees.fxml"));
        Parent root = loader.load();
        EntreesController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        ctrl.loadEntrees();
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void sorties_caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/sorties.fxml"));
        Parent root = loader.load();
        SortiesController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        ctrl.loadSorties();
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/caisse.fxml"));
        Parent root = loader.load();
        CaisseController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Parent root = loader.load();
        UtilisateurController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerUtilisateur(); } catch (SQLException ignored) {}
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/login.fxml"));
        Parent root = loader.load();
        LoginController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) valeur_depense_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void filtrer_historique_depense(ActionEvent event) throws SQLException {
        boolean okDebut = ValidationEntree.validerDateObligatoire(date_debut_historique_depense);
        boolean okFin = ValidationEntree.validerDateObligatoire(date_fin_historique_depense);
        if (okDebut && okFin) {
            historique_depense_table_view.getItems().clear();
            depenseObservableList.clear();

            depenseObservableList.setAll(depenseDAO.listerDepensefiltrer(date_debut_historique_depense.getValue(), date_fin_historique_depense.getValue()));
            historique_depense_table_view.setItems(depenseObservableList);

            valeur_depense_label.setText("TOTAL DEPENSE : " + depenseDAO.getTotalDepenseFiltre(date_debut_historique_depense.getValue(), date_fin_historique_depense.getValue()));
        } else {
            //appel alerte date erreur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CHAMP INVALIDE");
            alert.setHeaderText(null);
            alert.setContentText("Verifier la date entrée.");
            alert.showAndWait();
        }

    }

    private void filtre_historique_depense_par_defaut() throws SQLException {
        boolean okDebut = ValidationEntree.validerDateObligatoire(date_debut_historique_depense);
        boolean okFin = ValidationEntree.validerDateObligatoire(date_fin_historique_depense);
        if (okDebut && okFin) {
            historique_depense_table_view.getItems().clear();
            depenseObservableList.clear();

            depenseObservableList.setAll(depenseDAO.listerDepensefiltrer(date_debut_historique_depense.getValue(), date_fin_historique_depense.getValue()));
            historique_depense_table_view.setItems(depenseObservableList);

            valeur_depense_label.setText("TOTAL DEPENSE : " + depenseDAO.getTotalDepenseFiltre(date_debut_historique_depense.getValue(), date_fin_historique_depense.getValue()));
        } else {
            //appel alerte date erreur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CHAMP INVALIDE");
            alert.setHeaderText(null);
            alert.setContentText("Verifier la date entrée.");
            alert.showAndWait();
        }

    }

    public void chargerDepense() throws SQLException {
        if (depenseDAO != null) {
            depenseObservableList.setAll(depenseDAO.listerDepenseBDD());
        }
    }
    
}
