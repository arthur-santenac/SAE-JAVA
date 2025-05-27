
public class DetailCommande {
    private int numLig;
    private int qte;
    private double prixVente;
    private Livre livre;
    private int numCom;

    public DetailCommande(int numLig, int qte, double prixVente,Livre livre, int numCom) {
        this.numLig = numLig;
        this.qte = qte;
        this.prixVente = livre.getPrix()*qte;
        this.livre = livre;
        this.numCom = numCom;
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

    public Livre getLivre(){
        return this.livre;
    }

    public int numCom(){
        return this.numCom;
    }

    @Override
    public String toString(){
        String ligne = this.numLig+" ligne" ;
        String lignes = this.numLig+" lignes" ;
        if(this.numLig >1){
            return "La commande N°"+this.numCom+" contient "+lignes+" elle contient le livre"+this.livre.getIsbn()+" en "+this.qte+" exemplaire pour un prix de"+this.prixVente+"€";
        }
        else{
            return "La commande N°"+this.numCom+" contient "+ligne+" elle contient le livre"+this.livre.getIsbn()+" en "+this.qte+" exemplaire pour un prix de"+this.prixVente+"€";
        }
        
    }


}


