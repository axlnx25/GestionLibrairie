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
import app_model.Depense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author axlnx
 */
public class Nouvelle_depenseController implements Initializable {

    @FXML
    private TextField motif_depense_textfield;
    @FXML
    private DatePicker date_depense;
    @FXML
    private TextField montant_depense;

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

    public void setDepenseDAO_NouvelleDepense(DepenseDAO depense) {
        this.depenseDAO = depense;
    }
    ObservableList<Depense> listDepenseObservable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void afficher_tableau_de_bord(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Parent root = loader.load();
        Tableau_bordController ctrl = loader.getController();
        ctrl.setDAO_TableauBord(venteDAO, depenseDAO, approvisionnementDAO);
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) montant_depense.getScene().getWindow();
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
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Parent root = loader.load();
        Historique_venteController ctrl = loader.getController();
        ctrl.setFactureDAO_HistoriqueVente(factureDAO);
        ctrl.setVenteDAO_HistoriqueVente(venteDAO);
        Stage stage = (Stage) montant_depense.getScene().getWindow();
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
        Stage stage = (Stage) montant_depense.getScene().getWindow();
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
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Parent root = loader.load();
        Historique_depenseController ctrl = loader.getController();
        ctrl.setDepenseDAO_HistoriqueDepense(depenseDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Parent root = loader.load();
        Nouvelle_depenseController ctrl = loader.getController();
        ctrl.setDepenseDAO_NouvelleDepense(depenseDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/entrees.fxml"));
        Parent root = loader.load();
        EntreesController ctrl = loader.getController();
        ctrl.setVenteDAO_Entrees(venteDAO);
        Stage stage = (Stage) montant_depense.getScene().getWindow();
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
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/caisse.fxml"));
        Parent root = loader.load();
        CaisseController ctrl = loader.getController();
        ctrl.setDAO_Caisse(venteDAO, depenseDAO, approvisionnementDAO);
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Parent root = loader.load();
        UtilisateurController ctrl = loader.getController();
        ctrl.setUtilisateurDAO_Utilisateur(utilisateurDAO);
        try { ctrl.chargerUtilisateur(); } catch (SQLException ignored) {}
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/login.fxml"));
        Stage stage = (Stage) montant_depense.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void ajouter_depense(ActionEvent event) throws SQLException {
        boolean okMotif = ValidationEntree.validerTexteObligatoire(motif_depense_textfield);
        boolean okMontant = ValidationEntree.validerNombre(montant_depense);
        boolean okDate = ValidationEntree.validerDateObligatoire(date_depense);
        if (okMotif && okMontant && okDate) {
            Depense d = new Depense(
                    motif_depense_textfield.getText(),
                    Integer.parseInt(montant_depense.getText()),
                    date_depense.getValue(),
                    Session.getInstance().getIDUtilisateur()
            );
            listDepenseObservable.add(d);
            depenseDAO.ajouterDepenseBDD(d);

            motif_depense_textfield.clear();
            montant_depense.clear();
            date_depense.setValue(null);
        } else {
            //appel alerte verifier les champs
        }
    }

    @FXML
    private void annuler_depense(ActionEvent event) {
        motif_depense_textfield.clear();
        montant_depense.clear();
        date_depense.setValue(null);
    }

    public void chargerDepense() throws SQLException {
        if (depenseDAO != null) {
            listDepenseObservable.setAll(depenseDAO.listerDepenseBDD());
        }
    }
    
}
