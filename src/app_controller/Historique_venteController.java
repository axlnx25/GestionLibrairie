package app_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app_DTO.FactureDTO;
import app_dao.*;
import app_helper.GestionAcces;
import app_helper.ValidationEntree;
import app_model.Vente;
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

public class Historique_venteController implements Initializable {

    @FXML private DatePicker date_debut_historique_facture;
    @FXML private DatePicker date_fin_historique_facture;
    @FXML private TableView<Vente> facture_table_view;
    @FXML private TableColumn<Vente, LocalDate> colonne_date;
    @FXML private TableColumn<Vente, Integer> colonne_facture;
    @FXML private Label montant_facture_label;
    @FXML private TableView<FactureDTO> details_tableview;
    @FXML private TableColumn<FactureDTO, String> colonne_article;
    @FXML private TableColumn<FactureDTO, Integer> colonne_quantite;
    @FXML private TableColumn<FactureDTO, Integer> colonne_prix_unitaire;
    @FXML private Label remise_facture_label;
    @FXML private Label montant_vente_label;

    @FXML private  TitledPane tpUser;
    @FXML private  TitledPane tpTresor;
    @FXML private  TitledPane tpArticle;

    // --- DAO ---
    private VenteDAO venteDAO;
    private DepenseDAO depenseDAO;
    private ApprovisionnementDAO approvisionnementDAO;
    private ArticleDAO articleDAO;
    private TypeArticleDAO typeArticleDAO;
    private FactureDAO factureDAO;
    private UtilisateurDAO utilisateurDAO;

    // --- Données observables ---
    private final ObservableList<Vente> venteObservableList = FXCollections.observableArrayList();

    // --- Injection des DAO ---
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        GestionAcces.restrictForNonAdmin(tpUser, tpTresor, tpArticle);

        // Colonnes principales
        colonne_date.setCellValueFactory(data -> data.getValue().dateVenteProperty());
        colonne_facture.setCellValueFactory(data -> data.getValue().idVenteProperty().asObject());

        colonne_article.setCellValueFactory(data -> data.getValue().article_venduProperty());
        colonne_quantite.setCellValueFactory(data -> data.getValue().quantite_venduProperty().asObject());
        colonne_prix_unitaire.setCellValueFactory(data -> data.getValue().prix_venteProperty().asObject());

        facture_table_view.setItems(venteObservableList);

        // Lorsqu’on sélectionne une facture, on affiche ses détails
        facture_table_view.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null && factureDAO != null && venteDAO != null) {
                afficherDetailsFacture(newV);
            }
        });

        //  Définir le filtre par défaut du jour
        Platform.runLater(() -> {
            try {
                date_debut_historique_facture.setValue(LocalDate.now());
                date_fin_historique_facture.setValue(LocalDate.now());
                filtre_historique_facture_par_defaut();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // --- Afficher les détails d’une facture ---
    private void afficherDetailsFacture(Vente vente) {
        try {
            ArrayList<FactureDTO> details = factureDAO.listerFactureParIdVente(vente.getIdVente());
            details_tableview.setItems(FXCollections.observableArrayList(details));

            remise_facture_label.setText("REMISE : " + venteDAO.getRemiseVente(vente.getIdVente()));
            montant_vente_label.setText("MONTANT VENTE (S) : " + venteDAO.getTotalVente(vente.getIdVente()));
            montant_facture_label.setText("TOTAL FACTURE (S) : " + venteDAO.getTotalToutesVente());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Filtrage manuel par date ---
    @FXML
    private void filtre_historique_facture(ActionEvent event) throws SQLException {
        appliquerFiltrage(date_debut_historique_facture.getValue(), date_fin_historique_facture.getValue());
    }

    // --- Filtrage automatique par défaut (ventes du jour) ---
    private void filtre_historique_facture_par_defaut() throws SQLException {
        LocalDate aujourdHui = LocalDate.now();
        appliquerFiltrage(aujourdHui, aujourdHui);
    }

    private void appliquerFiltrage(LocalDate debut, LocalDate fin) throws SQLException {
        boolean okDebut = debut != null;
        boolean okFin = fin != null;

        if (okDebut && okFin) {
            remise_facture_label.setText("");
            montant_facture_label.setText("");
            montant_vente_label.setText("");

            venteObservableList.clear();
            venteObservableList.setAll(venteDAO.filtrerVenteDate(debut, fin));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CHAMP INVALIDE");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez vérifier les dates !");
            alert.showAndWait();
        }
    }

    // --- Chargement initial des ventes (optionnel) ---
    public void chargerVente() throws SQLException {
        if (venteDAO != null) {
            venteObservableList.setAll(venteDAO.obtenirVentesBDD());
        }
    }

    // --- Navigation entre vues ---
    @FXML private void afficher_tableau_de_bord(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Parent root = loader.load();
        Tableau_bordController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML private void nouvelle_vente(ActionEvent e) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_vente.fxml"));
        Parent root = loader.load();
        Nouvelle_venteController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        ctrl.loadCombo();
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML private void historique_vente(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Parent root = loader.load();
        Historique_venteController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML private void approvisionnement(ActionEvent e) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/approvisionnement.fxml"));
        Parent root = loader.load();
        ApprovisionnementController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        ctrl.loadCombo();
        ctrl.loadColArticle();
        ctrl.chargerApprovisionnement();
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML private void consulter_stock(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/stock_nouvelle_article.fxml"));
        Parent root = loader.load();
        Stock_nouvelle_articleController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerArticle(); ctrl.chargerTypeArticle(); } catch (SQLException ignored) {}
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML private void historique_depense(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Parent root = loader.load();
        Historique_depenseController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML private void nouvelle_depense(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Parent root = loader.load();
        Nouvelle_depenseController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML private void entrees_caisse(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/entrees.fxml"));
        Parent root = loader.load();
        EntreesController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        ctrl.loadEntrees();
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML private void sorties_caisse(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/sorties.fxml"));
        Parent root = loader.load();
        SortiesController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        ctrl.loadSorties();
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML private void caisse(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/caisse.fxml"));
        Parent root = loader.load();
        CaisseController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML private void utilisateur(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Parent root = loader.load();
        UtilisateurController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerUtilisateur(); } catch (SQLException ignored) {}
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML private void deconnexion(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/login.fxml"));
        Parent root = loader.load();
        LoginController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) montant_vente_label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
