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
import app_helper.ValidationEntree;
import app_model.Utilisateur;
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
public class UtilisateurController implements Initializable {

    @FXML
    private TableView<Utilisateur> utilisateur_tableview;
    @FXML
    private TableColumn<Utilisateur, String> colonne_nom;
    @FXML
    private TableColumn<Utilisateur, String> colonne_mot_de_passe;
    @FXML
    private TableColumn<Utilisateur, String> colonne_role;
    @FXML
    private TextField nom_textfied;
    @FXML
    private TextField mot_de_passe_textfield;
    @FXML
    private ComboBox<String> combo_role;

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

//    public void setUtilisateurDAO_Utilisateur(UtilisateurDAO utilisateurDAO) {
//        this.utilisateurDAO = utilisateurDAO;
//        // Rafraichir la liste dès l'injection
//        try {
//            chargerUtilisateur();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    ObservableList<Utilisateur> utilisateursObservableList = FXCollections.observableArrayList();
    ObservableList<String> roleObservableList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roleObservableList = FXCollections.observableArrayList(
                "Admin", "Vendeur", "Comptable", "Secretaire"
        );
        combo_role.setItems(roleObservableList);
        utilisateur_tableview.setItems(utilisateursObservableList);

        colonne_nom.setCellValueFactory(data -> data.getValue().nomUtilisateurProperty());
        colonne_mot_de_passe.setCellValueFactory(data -> data.getValue().motDePasseProperty());
        colonne_role.setCellValueFactory(data -> data.getValue().roleUtilisateurProperty());

        utilisateur_tableview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nom_textfied.setText(newValue.nomUtilisateurProperty().get());
                combo_role.setValue(newValue.roleUtilisateurProperty().get());
                mot_de_passe_textfield.setText(newValue.motDePasseProperty().get());
            }
        });
        // Charger si le DAO est déjà injecté
        if (utilisateurDAO != null) {
            try {
                chargerUtilisateur();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }    

    @FXML
    private void afficher_tableau_de_bord(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Parent root = loader.load();
        Tableau_bordController ctrl = loader.getController();
        ctrl.setDAO_TableauBord(venteDAO, depenseDAO, approvisionnementDAO);
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_vente(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_vente.fxml"));
        Parent root = loader.load();
        Nouvelle_venteController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        ctrl.loadCombo();
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Parent root = loader.load();
        Historique_venteController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
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
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void consulter_stock(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/stock_nouvelle_article.fxml"));
        Parent root = loader.load();
        Stock_nouvelle_articleController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerArticle(); ctrl.chargerTypeArticle(); } catch (SQLException ignored) {}
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Parent root = loader.load();
        Historique_depenseController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Parent root = loader.load();
        Nouvelle_depenseController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerDepense(); } catch (SQLException ignored) {}
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/entrees.fxml"));
        Parent root = loader.load();
        EntreesController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        ctrl.loadEntrees();
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void sorties_caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/sorties.fxml"));
        Parent root = loader.load();
        SortiesController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        ctrl.loadSorties();
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/caisse.fxml"));
        Parent root = loader.load();
        CaisseController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Parent root = loader.load();
        UtilisateurController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        try { ctrl.chargerUtilisateur(); } catch (SQLException ignored) {}
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/login.fxml"));
        Parent root = loader.load();
        LoginController ctrl = loader.getController();
        ctrl.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void ajouter_utilisateur(ActionEvent event) throws SQLException {
        boolean okNom = ValidationEntree.validerTexteObligatoire(nom_textfied);
        boolean okRole = ValidationEntree.validerCombobox(combo_role);
        boolean okMotDePasse = ValidationEntree.validerTexteObligatoire(mot_de_passe_textfield);
        if (okNom && okRole && okMotDePasse) {
            Utilisateur u = new Utilisateur(
                    nom_textfied.getText(),
                    combo_role.getValue(),
                    mot_de_passe_textfield.getText()
            );
            utilisateursObservableList.add(u);
            utilisateurDAO.ajouterUtilisateurBDD(u);

            nom_textfied.clear();
            mot_de_passe_textfield.clear();
            combo_role.setValue(null);
        } else {
            //appel alerte verifier les champs
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CHAMPS INVALIDES");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez verifier les informations saisies");
            alert.showAndWait();
        }
    }

    @FXML
    private void modifier_utilisateur(ActionEvent event) throws SQLException {
        Utilisateur selected = utilisateur_tableview.getSelectionModel().getSelectedItem();
        if  (selected != null) {
            boolean okNom = ValidationEntree.validerTexteObligatoire(nom_textfied);
            boolean okRole = ValidationEntree.validerCombobox(combo_role);
            boolean okMotDePasse = ValidationEntree.validerTexteObligatoire(mot_de_passe_textfield);
            if (okNom && okRole && okMotDePasse) {
                selected.setNomUtilisateur(nom_textfied.getText());
                selected.setRoleUtilisateur(combo_role.getValue());
                selected.setMotDePasse(mot_de_passe_textfield.getText());

                utilisateurDAO.modifierUtilisateurBDD(selected);

                nom_textfied.clear();
                mot_de_passe_textfield.clear();
                combo_role.setValue(null);
            } else {
                //appel alerte verifier les champs
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("CHAMPS INVALIDES");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez verifier les informations saisies");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void supprimer_utilisateur(ActionEvent event) throws SQLException {
        Utilisateur selected = utilisateur_tableview.getSelectionModel().getSelectedItem();
        if (selected != null) {
            utilisateursObservableList.remove(selected);
            utilisateurDAO.supprimerUtilisateurBDD(selected);

            nom_textfied.clear();
            mot_de_passe_textfield.clear();
            combo_role.setValue(null);
        } else {
            //appel alerte selection nulle
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERREUR SELECTION");
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez rien selectionner selectionner.");
            alert.showAndWait();
        }
    }

    public void chargerUtilisateur() throws SQLException {
        if (utilisateurDAO != null) {
            utilisateursObservableList.setAll(utilisateurDAO.obenirUtilisateurBDD());
        }
    }
    
}
