
public class DetailCommande {
    
    private int numLig;
    private int qte;
    private double prixVente;
    private Livre livre;
    private Commande commande;

    public DetailCommande(int numLig, int qte, double prixVente,Livre livre, Commande com) {
        this.numLig = numLig;
        this.qte = qte;
        this.prixVente = livre.getPrix()*qte;
        this.livre = livre;
        this.commande = com;
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
