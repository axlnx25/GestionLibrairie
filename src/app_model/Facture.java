package app_model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Facture {
    private IntegerProperty idVente = new SimpleIntegerProperty();
    private IntegerProperty idArticle = new SimpleIntegerProperty();
    private IntegerProperty quantite = new SimpleIntegerProperty();

    public Facture(int idVente, int idArticle, int quantite) {
        this.idVente.set(idVente);
        this.idArticle.set(idArticle);
        this.quantite.set(quantite);
    }

    public int getIdVente() {
        return this.idVente.get();
    }

   public int getIdArticle() {
        return this.idArticle.get();
   }

   public int getQuantite() {
        return this.quantite.get();
   }

   public void setIdVente(int idVente) {
        this.idVente.set(idVente);
   }

   public void setIdArticle(int idArticle) {
        this.idArticle.set(idArticle);
   }

   public void setQuantite(int quantite) {
        this.quantite.set(quantite);
   }

   public IntegerProperty idVenteProperty() {
        return this.idVente;
   }

   public IntegerProperty idArticleProperty() {
        return this.idArticle;
   }

   public IntegerProperty quantiteProperty() {
        return this.quantite;
   }
}
