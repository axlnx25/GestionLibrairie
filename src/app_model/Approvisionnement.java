package app_model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Date;

public class Approvisionnement {
    private IntegerProperty idApprovisionnement = new SimpleIntegerProperty();
    private StringProperty stockOrigine = new SimpleStringProperty();
    private IntegerProperty quantiteApprovisionne = new SimpleIntegerProperty();
    private ObjectProperty<LocalDate> dateApprovisionnement = new SimpleObjectProperty<>();
    private IntegerProperty montantApprovisionnement = new SimpleIntegerProperty();
    private IntegerProperty numeroArticle = new SimpleIntegerProperty();
    private IntegerProperty codeUtilisateur = new SimpleIntegerProperty();
    private StringProperty nomArticle = new SimpleStringProperty();


    public Approvisionnement (String stock_Origine, int quantite_Approvisionne, LocalDate date_Approvisionnement, int montantApprovisionne, int numero_Article, int codeUtilisateur) {
        this.stockOrigine.set(stock_Origine);
        this.quantiteApprovisionne.set(quantite_Approvisionne);
        this.dateApprovisionnement.set(date_Approvisionnement);
        this.montantApprovisionnement.set(montantApprovisionne);
        this.numeroArticle.set(numero_Article);
        this.codeUtilisateur.set(codeUtilisateur);
    }

    public int getIdApprovisionnement() {
        return idApprovisionnement.get();
    }

    public String getStockOrigine() {
        return stockOrigine.get();
    }

    public int getQuantiteApprovisionne() {
        return quantiteApprovisionne.get();
    }

    public LocalDate getDateApprovisionnement() {
        return dateApprovisionnement.get();
    }

    public int getMontantApprovisionne() {
        return montantApprovisionnement.get();
    }

    public int getNumeroArticle() {
        return numeroArticle.get();
    }

    public int getCodeUtilisateur() {
        return codeUtilisateur.get();
    }

    public void setIdApprovisionnement(int idApprovisionnement) {
        this.idApprovisionnement.set(idApprovisionnement);
    }

    public void setStockOrigine(String stock_Origine) {
        this.stockOrigine.set(stock_Origine);
    }

    public void setQuantiteApprovisionne(int quantite_Approvisionne) {
        this.quantiteApprovisionne.set(quantite_Approvisionne);
    }

    public void setDateApprovisionnement(LocalDate date_Approvisionnement) {
        this.dateApprovisionnement.set(date_Approvisionnement);
    }

    public void setMontantApprovisionne(int montant_Approvisionne) {
        this.montantApprovisionnement.set(montant_Approvisionne);
    }

    public void setNumeroArticle(int numero_Article) {
        this.numeroArticle.set(numero_Article);
    }

    public void setCodeUtilisateur(int code_utilisateur) {
        this.codeUtilisateur.set(code_utilisateur);
    }

    public IntegerProperty idApprovisionnementProperty() {
        return idApprovisionnement;
    }

    public StringProperty stockOrigineProperty() {
        return stockOrigine;
    }

    public IntegerProperty quantiteApprovisionneProperty() {
        return quantiteApprovisionne;
    }

    public ObjectProperty<LocalDate> dateApprovisionnementProperty() {
        return dateApprovisionnement;
    }

    public IntegerProperty montantApprovisionnementProperty() {
        return montantApprovisionnement;
    }

    public IntegerProperty numeroArticleProperty() {
        return numeroArticle;
    }

    public IntegerProperty codeUtilisateurProperty() {
        return codeUtilisateur;
    }

    public StringProperty nomArticleProperty() {
        return nomArticle;
    }

    public String getNomArticle() {
        return nomArticle.get();
    }

    public void setNomArticle(String nom) {
        this.nomArticle.set(nom);
    }

}
