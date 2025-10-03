package app_model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Type_Article {
    private IntegerProperty idTypeArticle = new SimpleIntegerProperty();
    private StringProperty typeArticle = new SimpleStringProperty();
    private IntegerProperty codeUtilisateur = new SimpleIntegerProperty();

    public Type_Article(String typeArticle, int codeUtilisateur) {
        this.typeArticle.set(typeArticle);
        this.codeUtilisateur.set(codeUtilisateur);
    }

    public int getIdTypeArticle() {
        return idTypeArticle.get();
    }

    public String getTypeArticle() {
        return typeArticle.get();
    }

    public int getCodeUtilisateur() {
        return codeUtilisateur.get();
    }

    public void setIdTypeArticle(int idTypeArticle) {
        this.idTypeArticle.set(idTypeArticle);
    }

    public void setTypeArticle(String typeArticle) {
        this.typeArticle.set(typeArticle);
    }

    public void setCodeUtilisateur(int codeUtilisateur) {
        this.codeUtilisateur.set(codeUtilisateur);
    }

    public IntegerProperty idTypeArticleProperty() {
        return idTypeArticle;
    }

    public StringProperty typeArticleProperty() {
        return typeArticle;
    }

    public IntegerProperty codeUtilisateurProperty() {
        return codeUtilisateur;
    }
}
