/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package app_main;

import app_controller.*;
import app_dao.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author axlnx
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        Connection connection = ConnectionDataBase.getConnection();

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO(connection);
        FactureDAO  factureDAO = new FactureDAO(connection);
        DepenseDAO depenseDAO = new DepenseDAO(connection);
        TypeArticleDAO typeArticleDAO = new TypeArticleDAO(connection);
        VenteDAO venteDAO = new VenteDAO(connection);
        ArticleDAO articleDAO = new ArticleDAO(connection);
        ApprovisionnementDAO approvisionnementDAO = new ApprovisionnementDAO(connection);

        //chargement Loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app_fxml/login.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();

        FXMLLoader loaderAppro = new FXMLLoader(getClass().getResource("/app_fxml/approvisionnement.fxml"));
        Parent rootAppro = loader.load();
        ApprovisionnementController approvisionnementController = loaderAppro.getController();

        FXMLLoader loaderCaisse = new FXMLLoader(getClass().getResource("/app_fxml/caisse.fxml"));
        Parent rootCaisse = loader.load();
        CaisseController caisseController = loaderCaisse.getController();

        FXMLLoader loaderEntree = new FXMLLoader(getClass().getResource("/app_fxml/entrees_caisse.fxml"));
        Parent rootEntree = loader.load();
        EntreesController entreesController = loaderEntree.getController();

        FXMLLoader loaderHistorique_Depense = new FXMLLoader(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Parent rootHistorique_Depense = loader.load();
        Historique_depenseController historique_depenseController = loaderHistorique_Depense.getController();

        FXMLLoader loaderHistorique_Vente = new FXMLLoader(getClass().getResource("/app_fxml/historique_vente.fxml"));
        Parent rootHistorique_Vente = loader.load();
        Historique_venteController historiqueVenteController = loaderHistorique_Vente.getController();

        FXMLLoader loaderNouvelle_Depense = new FXMLLoader(getClass().getResource("/app_fxml/historique_depense.fxml"));
        Parent rootNouvelle_Depense = loader.load();
        Nouvelle_depenseController nouvelleDepenseController = loaderNouvelle_Depense.getController();

        FXMLLoader loaderNouvelle_Vente = new FXMLLoader(getClass().getResource("/app_fxml/nouvelle_vente.fxml"));
        Parent rootNouvelle_Vente = loader.load();
        Nouvelle_venteController nouvelleVenteController = loaderNouvelle_Vente.getController();

        FXMLLoader loaderSorties = new FXMLLoader(getClass().getResource("/app_fxml/sorties.fxml"));
        Parent rootSorties = loader.load();
        SortiesController sortiesController = loaderSorties.getController();

        FXMLLoader loaderStock = new FXMLLoader(getClass().getResource("/app_fxml/stock_nouvelle_article.fxml"));
        Parent rootStock = loader.load();
        Stock_nouvelle_articleController stock_nouvelle_articleController = loaderStock.getController();

        FXMLLoader loaderTableau = new FXMLLoader(getClass().getResource("/app_fxml/tableau_bord.fxml"));
        Parent rootTableau = loader.load();
        Tableau_bordController tableau_bordController = loaderTableau.getController();

        FXMLLoader loaderType = new FXMLLoader(getClass().getResource("/app_fxml/type_article.fxml"));
        Parent rootType = loader.load();
        Type_articleController type_articleController = loaderType.getController();

        FXMLLoader loaderUtilisateur = new FXMLLoader(getClass().getResource("/app_fxml/utilisateur.fxml"));
        Parent rootUtilisateur = loader.load();
        UtilisateurController utilisateurController = loaderUtilisateur.getController();





        //utiliser les setters dans les controller
        loginController.setUtilisateurDAO_login(utilisateurDAO);
        approvisionnementController.setApprovisionnementDAO_Appro(approvisionnementDAO);
        approvisionnementController.setArticleDAO_Appro(articleDAO);
        caisseController.setDAO_Caisse(venteDAO, depenseDAO, approvisionnementDAO);
        entreesController.setVenteDAO_Entrees(venteDAO);
        historique_depenseController.setDepenseDAO_HistoriqueDepense(depenseDAO);
        historiqueVenteController.setFactureDAO_HistoriqueVente(factureDAO);
        historiqueVenteController.setVenteDAO_HistoriqueVente(venteDAO);
        nouvelleDepenseController.setDepenseDAO_NouvelleDepense(depenseDAO);
        nouvelleVenteController.setArticleDAO_NouvelleVenteDAO(articleDAO);
        nouvelleVenteController.setFactureDAO_NouvelleVente(factureDAO);
        nouvelleVenteController.setVenteDAO_NouvelleVenteDAO(venteDAO);
        sortiesController.setApprovisionnementDAO(approvisionnementDAO);
        sortiesController.setDepenseDAO_Sorties(depenseDAO);
        sortiesController.setArticleDAO(articleDAO);
        stock_nouvelle_articleController.setTypeArticleDAO_Stock(typeArticleDAO);
        stock_nouvelle_articleController.setArticleDAO_Stock(articleDAO);
        tableau_bordController.setDAO_TableauBord(venteDAO, depenseDAO, approvisionnementDAO);
        type_articleController.setTypeArticleDAO_TypeArticle(typeArticleDAO);
        utilisateurController.setUtilisateurDAO_Utilisateur(utilisateurDAO);


        //charger les données depuis la BDD
        approvisionnementController.chargerApprovisionnement();
        historique_depenseController.chargerDepense();
        historiqueVenteController.chargerVente();
        nouvelleDepenseController.chargerDepense();
        stock_nouvelle_articleController.chargerArticle();
        stock_nouvelle_articleController.chargerTypeArticle();
        type_articleController.chargerTypeArticle();
        utilisateurController.chargerUtilisateur();

        Scene scene = new Scene(root);

        primaryStage.setTitle("GESTION LIBRAIRIE");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
