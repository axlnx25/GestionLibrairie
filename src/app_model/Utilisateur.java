package app_model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Utilisateur {
    private IntegerProperty idUtilisateur = new SimpleIntegerProperty();
    private StringProperty nomUtilisateur = new SimpleStringProperty();
    private StringProperty roleUtilisateur = new SimpleStringProperty();
    private StringProperty motDePasse = new SimpleStringProperty();

    public Utilisateur(String nomUtilisateur, String roleUtilisateur, String motDePasse) {
        this.nomUtilisateur.set(nomUtilisateur);
        this.roleUtilisateur.set(roleUtilisateur);
        this.motDePasse.set(motDePasse);
    }

    public int getIdUtilisateur() {
        return this.idUtilisateur.get();
    }

    public String getNomUtilisateur() {
        return this.nomUtilisateur.get();
    }

    public String getRoleUtilisateur() {
        return this.roleUtilisateur.get();
    }

    public String getMotDePasse() {
        return this.motDePasse.get();
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur.set(idUtilisateur);
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur.set(nomUtilisateur);
    }

    public void setRoleUtilisateur(String roleUtilisateur) {
        this.roleUtilisateur.set(roleUtilisateur);
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse.set(motDePasse);
    }

    public IntegerProperty idUtilisateurProperty() {
        return this.idUtilisateur;
    }

    public StringProperty nomUtilisateurProperty() {
        return this.nomUtilisateur;
    }

    public StringProperty roleUtilisateurProperty() {
        return this.roleUtilisateur;
    }

    public StringProperty motDePasseProperty() {
        return this.motDePasse;
    }
}
