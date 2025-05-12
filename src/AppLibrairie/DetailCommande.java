package AppLibrairie;
public class DetailCommande {
    
    private int numLig;
    private int qte;
    private double prixVente;

    public DetailCommande(int numLig, int qte, double prixVente) {
        this.numLig = numLig;
        this.qte = qte;
        this.prixVente = prixVente;
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

}
