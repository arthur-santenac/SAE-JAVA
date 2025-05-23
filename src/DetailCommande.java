
public class DetailCommande {
    private int numLig;
    private int qte;
    private double prixVente;
    private Livre livre;

    public DetailCommande(int numLig, int qte, double prixVente, Livre livre) {
        this.numLig = numLig;
        this.qte = qte;
        this.livre = livre;
        this.prixVente = this.qte * this.livre.getPrix();
    }

    public int getNumLig() {
        return numLig;
    }

    public int getQte() {
        return qte;
    }

    public double getPrixVente() {
        return prixVente;
    }
}


