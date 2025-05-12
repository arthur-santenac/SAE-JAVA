

public class Vendeur extends Personne{
    
    private int idVendeur;

    public Vendeur(String nom, String prenom, String adresse, int codePostal, String ville, int idVendeur) {
        super(nom, prenom, adresse, codePostal, ville);
        this.idVendeur = idVendeur;
    }

    public int getIdVendeur() {
        return idVendeur;
    }

    public void setIdVendeur(int idVendeur) {
        this.idVendeur = idVendeur;            
    }

}