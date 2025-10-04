/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package app_main;

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

        //utiliser les setters dans les controller

        Parent root = FXMLLoader.load(getClass().getResource("/app_fxml/login.fxml"));

        //charger les données depuis la BDD

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
