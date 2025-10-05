/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import app_dao.UtilisateurDAO;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    private UtilisateurDAO utilisateurDAO;
    public void setUtilisateurDAO_Utilisateur(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

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
    }    

    @FXML
    private void afficher_tableau_de_bord(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_vente.fxml"));
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_vente(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void approvisionnement(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/approvisionnement.fxml"));
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void consulter_stock(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/stock_nouvelle_article.fxml"));
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void historique_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void nouvelle_depense(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/nouvelle_depense.fxml"));
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void entrees_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/entrees.fxml"));
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void sorties_caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/sorties.fxml"));
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void caisse(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/caisse.fxml"));
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void utilisateur(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Stage stage = (Stage) nom_textfied.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void deconnexion(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("/app_fxml/login.fxml"));
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
        }
    }

    public void chargerUtilisateur() throws SQLException {
        if (utilisateurDAO != null) {
            utilisateursObservableList.setAll(utilisateurDAO.obenirUtilisateurBDD());
        }
    }
    
}
