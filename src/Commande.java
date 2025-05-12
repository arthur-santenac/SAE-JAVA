public class Commande {

    private int numCom;
    private String dateCom;
    private String enLigne;
    private String livraison;
    private Magasin mag;

    public Commande(int numCom, String dateCom, String enLigne, String livraison, Magasin mag){
        this.numCom = numCom;
        this.dateCom = dateCom;
        this.enLigne = enLigne;
        this.livraison = livraison;
        this.mag = mag;
    }

    public int getNumCom() {
        return this.numCom;
    }

    public String getDateCom() {
        return this.dateCom;
    }

    public String getCommande() {
        return this.enLigne;
    }

    public String getLivraison() {
        return this.livraison;
    }

    public Magasin getMagasin(){
        return this.mag;
    }
}
