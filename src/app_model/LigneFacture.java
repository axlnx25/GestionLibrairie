package app_model;

import javafx.beans.property.*;

public class LigneFacture {
    private Article article;
    private IntegerProperty quantite;
    private IntegerProperty prixUnitaire;

    public LigneFacture(Article article, int quantite) {
        this.article = article;
        this.quantite = new SimpleIntegerProperty(quantite);
        this.prixUnitaire = new SimpleIntegerProperty(article.getPrixVenteArticle());
    }

    public Article getArticle() {
        return article;
    }

    public StringProperty designationArticleProperty() {
        return new SimpleStringProperty(article.getNomArticle());
    }

    public IntegerProperty quantiteProperty() {
        return quantite;
    }

    public int getQuantite() {
        return quantite.get();
    }

    public void setQuantite(int quantite) {
        this.quantite.set(quantite);
    }

    public IntegerProperty prixUnitaireProperty() {
        return prixUnitaire;
    }
}

