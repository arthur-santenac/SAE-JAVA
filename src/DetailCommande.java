
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
        return numLig;
    }

    public int getQte() {
        return qte;
    }

    public double getPrixVente() {
        return prixVente;
    }
}


