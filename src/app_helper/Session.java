package app_helper;

import app_model.Utilisateur;

public class Session {
    private static Session instance = null;
    private Utilisateur utilisateur;

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public int getIDUtilisateur() {
        return utilisateur != null ? utilisateur.getIdUtilisateur() : -1;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

}
