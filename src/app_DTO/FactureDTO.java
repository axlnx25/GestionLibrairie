package app_DTO;

import app_dao.VenteDAO;
import javafx.beans.property.*;

import java.sql.SQLException;
import java.time.LocalDate;

public class FactureDTO {
    private IntegerProperty id_vente  = new SimpleIntegerProperty();
    private IntegerProperty id_article = new SimpleIntegerProperty();
    private StringProperty article_vendu =  new SimpleStringProperty();
    private IntegerProperty quantite_vendu =  new SimpleIntegerProperty();
    private ObjectProperty<LocalDate> date_vente = new SimpleObjectProperty<>();
    private IntegerProperty prix_vente =  new SimpleIntegerProperty();
    private IntegerProperty remise_vente =  new SimpleIntegerProperty();

    public FactureDTO(int id_vente,int id_article, String nom_article_vendu, int quantite_vendu, LocalDate date_vente, int prix_vente, int remise_vente) {
        this.id_vente.set(id_vente);
        this.id_article.set(id_article);
        this.article_vendu.set(nom_article_vendu);
        this.quantite_vendu.set(quantite_vendu);
        this.date_vente.set(date_vente);
        this.prix_vente.set(prix_vente);
        this.remise_vente.set(remise_vente);
    }

    public int getId_vente() {
        return id_vente.get();
    }

    public int getId_article() {
        return id_article.get();
    }

    public String getArticle_vendu() {
        return article_vendu.get();
    }

    public int getQuantite_vendu() {
        return quantite_vendu.get();
    }

    public LocalDate getDate_vente() {
        return date_vente.get();
    }

    public int getPrix_vente() {
        return prix_vente.get();
    }

    public int getRemise_vente() {
        return remise_vente.get();
    }

    public String getIDFacture () {
        return (id_vente.get() + "-" + id_article.get());
    }

    public void setId_vente(int id_vente) {
        this.id_vente.set(id_vente);
    }

    public void setId_article(int id_article) {
        this.id_article.set(id_article);
    }

    public void setArticle_vendu(String article_vendu) {
        this.article_vendu.set(article_vendu);
    }

    public void setQuantite_vendu(int quantite_vendu) {
        this.quantite_vendu.set(quantite_vendu);
    }

    public void setDate_vente(LocalDate date_vente) {
        this.date_vente.set(date_vente);
    }

    public void setPrix_vente(int prix_vente) {
        this.prix_vente.set(prix_vente);
    }

    public void setRemise_vente(int remise_vente) {
        this.remise_vente.set(remise_vente);
    }

    public  IntegerProperty id_venteProperty() {
        return id_vente;
    }

    public  IntegerProperty id_articleProperty() {
        return id_article;
    }

    public  StringProperty article_venduProperty() {
        return article_vendu;
    }

    public  IntegerProperty quantite_venduProperty() {
        return quantite_vendu;
    }

    public  ObjectProperty<LocalDate> date_venteProperty() {
        return date_vente;
    }

    public  IntegerProperty prix_venteProperty() {
        return prix_vente;
    }

    public  IntegerProperty remise_venteProperty() {
        return remise_vente;
    }

    public StringProperty idFactureProperty() {
        StringProperty result = new SimpleStringProperty(getIDFacture());
        return (result);
    }

    private VenteDAO venteDAO;
    public void  setVenteDAO(VenteDAO venteDAO) {
        this.venteDAO = venteDAO;
    }
    public int remiseVente(int id_vente) throws SQLException {
        return venteDAO.getRemiseVente(id_vente);
    }
    public int totalVente_Facture(int id_vente) throws SQLException {
        return venteDAO.getTotalVente(id_vente);
    }
    public int totalToutesFactures () throws SQLException {
        return venteDAO.getTotalToutesVente();
    }

}
