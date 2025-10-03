package app_model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Facture {
    private IntegerProperty idVente = new SimpleIntegerProperty();
    private IntegerProperty idArticle = new SimpleIntegerProperty();

    public Facture(int idVente, int idArticle) {
        this.idVente.set(idVente);
        this.idArticle.set(idArticle);
    }

    public int getIdVente() {
        return this.idVente.get();
    }

   public int getIdArticle() {
        return this.idArticle.get();
   }

   public void setIdVente(int idVente) {
        this.idVente.set(idVente);
   }

   public void setIdArticle(int idArticle) {
        this.idArticle.set(idArticle);
   }

   public IntegerProperty idVenteProperty() {
        return this.idVente;
   }

   public IntegerProperty idArticleProperty() {
        return this.idArticle;
   }
}
