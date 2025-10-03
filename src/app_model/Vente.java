package app_model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.util.Date;

public class Vente {
    private IntegerProperty idVente = new SimpleIntegerProperty();
    private IntegerProperty quantiteVendu = new SimpleIntegerProperty();
    private ObjectProperty<LocalDate> dateVente = new SimpleObjectProperty<>();
    private IntegerProperty remiseVente = new SimpleIntegerProperty();
    private IntegerProperty codeUtilisateur = new SimpleIntegerProperty();

    public Vente(int quantiteVendu, LocalDate dateVente, int remiseVente, int codeUtilisateur) {
        this.quantiteVendu.set(quantiteVendu);
        this.dateVente.set(dateVente);
        this.remiseVente.set(remiseVente);
        this.codeUtilisateur.set(codeUtilisateur);
    }

    public int getIdVente() {
        return idVente.get();
    }

    public int getQuantiteVendu() {
        return quantiteVendu.get();
    }

    public LocalDate getDateVente() {
        return dateVente.get();
    }

    public int  getRemiseVente() {
        return remiseVente.get();
    }

    public int getCodeUtilisateur() {
        return codeUtilisateur.get();
    }

    public void setIdVente(int idVente) {
        this.idVente.set(idVente);
    }

    public void setQuantiteVendu(int quantiteVendu) {
        this.quantiteVendu.set(quantiteVendu);
    }

    public void setDateVente(LocalDate dateVente) {
        this.dateVente.set(dateVente);
    }

    public void setRemiseVente(int remiseVente) {
        this.remiseVente.set(remiseVente);
    }

    public void setCodeUtilisateur(int codeUtilisateur) {
        this.codeUtilisateur.set(codeUtilisateur);
    }

    public IntegerProperty idVenteProperty() {
        return idVente;
    }

    public IntegerProperty quantiteVenduProperty() {
        return quantiteVendu;
    }

    public ObjectProperty<LocalDate> dateVenteProperty() {
        return dateVente;
    }

    public IntegerProperty remiseVenteProperty() {
        return remiseVente;
    }

    public IntegerProperty codeUtilisateurProperty() {
        return codeUtilisateur;
    }

}
