public class Commande {

    private int numCom;
    private String dateCom;
    private String enLigne;
    private String livraison;

    public Commande(int numCom, String dateCom, String enLigne, String livraison){
        this.numCom = numCom;
        this.dateCom = dateCom;
        this.enLigne = enLigne;
        this.livraison = livraison;
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
}
