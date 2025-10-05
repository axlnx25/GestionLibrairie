package app_DTO;

public class EntreeDTO {
    private int idVente;
    private int montant;

    public EntreeDTO(int idVente, int montant) {
        this.idVente = idVente;
        this.montant = montant;
    }

    public int getIdVente() { return idVente; }
    public int getMontant() { return montant; }
}

