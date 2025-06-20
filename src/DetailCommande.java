public class DetailCommande {
    private int qte;
    private double prixVente;
    private Livre livre;
    private int numCom;

    public DetailCommande(int qte, double prixVente, Livre livre, int numCom) {
        this.qte = qte;
        this.prixVente = livre.getPrix()*qte;
        this.livre = livre;
        this.numCom = numCom;
    }

    public int getQte() {
        return qte;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public Livre getLivre(){
        return this.livre;
    }

    public int numCom(){
        return this.numCom;
    }

    @Override
    public String toString() {
        return this.livre.getTitre() + " " + this.qte + " " + this.prixVente;
    }

}


