package app_model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Depense {
    private IntegerProperty idDepense = new SimpleIntegerProperty();
    private StringProperty designationDepense = new SimpleStringProperty();
    private IntegerProperty montantDepense = new SimpleIntegerProperty();
    private ObjectProperty<LocalDate> dateDepense = new SimpleObjectProperty<>();
    private IntegerProperty codeUtilisateur = new SimpleIntegerProperty();

    public Depense(String designationDepense, int montantDepense,LocalDate dateDepense, int codeUtilisateur) {
        this.designationDepense.set(designationDepense);
        this.montantDepense.set(montantDepense);
        this.dateDepense.set(dateDepense);
        this.codeUtilisateur.set(codeUtilisateur);
    }

    public int getIdDepense() {
        return idDepense.get();
    }

    public String getDesignationDepense() {
        return designationDepense.get();
    }

    public int getMontantDepense() {
        return montantDepense.get();
    }

    public LocalDate getDateDepense() {
        return dateDepense.get();
    }

    public int getCodeUtilisateur() {
        return codeUtilisateur.get();
    }

    public void setIdDepense(int idDepense) {
        this.idDepense.set(idDepense);
    }

    public void setDesignationDepense(String designationDepense) {
        this.designationDepense.set(designationDepense);
    }

    public void setMontantDepense(int montantDepense) {
        this.montantDepense.set(montantDepense);
    }

    public void setDateDepense(LocalDate dateDepense) {
        this.dateDepense.set(dateDepense);
    }

    public void setCodeUtilisateur(int codeUtilisateur) {
        this.codeUtilisateur.set(codeUtilisateur);
    }

    public IntegerProperty idDepenseProperty() {
        return this.idDepense;
    }

    public StringProperty designationDepenseProperty() {
        return this.designationDepense;
    }

    public IntegerProperty montantDepenseProperty() {
        return this.montantDepense;
    }

    public ObjectProperty<LocalDate> dateDepenseProperty() {
        return this.dateDepense;
    }

    public IntegerProperty codeUtilisateurProperty() {
        return this.codeUtilisateur;
    }

}
