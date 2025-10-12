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
        // Initialiser uniquement ce qui est nécessaire à l'écran de connexion
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

        // Ne pas précharger les autres vues ici pour éviter d'appeler initialize()
        // avant l'injection des DAO. Elles seront chargées après connexion.





        // Injection nécessaire pour l'écran de connexion et pour la suite (réutilisation des DAO)
        loginController.setUtilisateurDAO_login(utilisateurDAO);
        loginController.setAllDAO(venteDAO, depenseDAO, approvisionnementDAO, articleDAO, typeArticleDAO, factureDAO, utilisateurDAO);


        // Ne rien charger tant que l'utilisateur n'est pas connecté

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
