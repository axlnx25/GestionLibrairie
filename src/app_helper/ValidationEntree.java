package app_helper;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class ValidationEntree {
    // Vérifie que le champ texte n’est pas vide
    public static boolean validerTexteObligatoire(TextField champ) {
        String valeur = champ.getText().trim();
        if (valeur.isEmpty()) {
            afficherErreur(champ);
            return false;
        }
        clearErreur(champ);
        return true;
    }

    // Vérifie que le champ est un nombre (double)
    public static boolean validerNombre(TextField champ) {
        try {
            double val = Double.parseDouble(champ.getText().trim());
            if (val < 0) {
                afficherErreur(champ);
                return false;
            }
            clearErreur(champ);
            return true;
        } catch (NumberFormatException e) {
            afficherErreur(champ);
            return false;
        }
    }

    public static boolean validerCombobox(ComboBox<String> comboBox) {
        if (comboBox.getValue().isEmpty() ||  comboBox.getValue() == null) {
            return  false;
        } else return  true;
    }

    // Vérifie que le champ est un nombre positif
    public static boolean validerNombrePositif(TextField champ) {
        try {
            double val = Double.parseDouble(champ.getText().trim());
            if (val <= 0) {
                afficherErreur(champ);
                return false;
            }
            clearErreur(champ);
            return true;
        } catch (NumberFormatException e) {
            afficherErreur(champ);
            return false;
        }
    }

    // Marque le champ invalide
    private static void afficherErreur(TextField champ) {
        champ.setStyle("-fx-border-color: red; -fx-border-width: 2;");
    }

    // Réinitialise le style
    private static void clearErreur(TextField champ) {
        champ.setStyle("");
    }

    public static boolean validerDateObligatoire(DatePicker picker) {
        LocalDate date = picker.getValue();
        if (date == null) {
            afficherErreurDate(picker);
            return false;
        }
        clearErreurDate(picker);
        return true;
    }

    private static void afficherErreurDate(DatePicker picker) {
        picker.setStyle("-fx-border-color: red; -fx-border-width: 2;");
    }

    private static void clearErreurDate(DatePicker picker) {
        picker.setStyle("");
    }
}
