
public class DetailCommande {
    
    private int numLig;
    private int qte;
    private double prixVente;
    private int numCom;
    private Livre livre;

    public DetailCommande(int numLig, int qte, double prixVente, int numCom, Livre livre) {
        this.numLig = numLig;
        this.qte = qte;
        this.prixVente = prixVente;
        this.numCom = numCom;
        this.livre = livre;
    }

    public int getNumLig() {
        return this.numLig;
    }

    public int getQte() {
        return this.qte;
    }

    public double getPrixVente() {
        return this.prixVente;
    }

    public int getCommande(){
        return this.numCom;
    }

}
