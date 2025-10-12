package app_model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Article {
    private IntegerProperty idArticle = new SimpleIntegerProperty();
    private IntegerProperty quantiteArticle = new SimpleIntegerProperty();
    private IntegerProperty prixVenteArticle = new SimpleIntegerProperty();
    private StringProperty nomArticle = new SimpleStringProperty();
    private StringProperty typeArticle = new SimpleStringProperty();
    private IntegerProperty codeUtilisateur = new SimpleIntegerProperty();

    public Article(int quantite_Article, int prix_Vente_Article, String nom_Article, String type_Article, int codeUtilisateur) {
        this.quantiteArticle.set(quantite_Article);
        this.prixVenteArticle.set(prix_Vente_Article);
        this.nomArticle.set(nom_Article);
        this.typeArticle.set(type_Article);
        this.codeUtilisateur.set(codeUtilisateur);
    }

    public int getIdArticle() {
        return this.idArticle.get();
    }

    public int getQuantiteArticle() {
        return this.quantiteArticle.get();
    }

    public int getPrixVenteArticle() {
        return this.prixVenteArticle.get();
    }

    public String getNomArticle() {
        return this.nomArticle.get();
    }

    public String getTypeArticle() {
        return this.typeArticle.get();
    }

    public int getCodeUtilisateur() {
        return this.codeUtilisateur.get();
    }

    public void setIdArticle(int idArticle) {
        this.idArticle.set(idArticle);
    }

    public void setQuantiteArticle(int quantite_Article) {
        this.quantiteArticle.set(quantite_Article);
    }

    public void setPrixVenteArticle(int prix_Vente_Article) {
        this.prixVenteArticle.set(prix_Vente_Article);
    }

    public void setNomArticle(String nom_Article) {
        this.nomArticle.set(nom_Article);
    }

    public void setTypeArticle(String type_Article) {
        this.typeArticle.set(type_Article);
    }

    public void setCodeUtilisateur(int codeUtilisateur) {
        this.codeUtilisateur.set(codeUtilisateur);
    }

    public IntegerProperty idArticleProperty() {
        return this.idArticle;
    }

    public IntegerProperty quantiteArticleProperty() {
        return this.quantiteArticle;
    }

    public IntegerProperty prixVenteArticleProperty() {
        return this.prixVenteArticle;
    }

    public StringProperty nomArticleProperty() {
        return this.nomArticle;
    }

    public StringProperty typeArticleProperty() {
        return this.typeArticle;
    }

    public IntegerProperty codeUtilisateurProperty() {
        return this.codeUtilisateur;
    }

}
