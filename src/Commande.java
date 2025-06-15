import java.util.ArrayList;
import java.util.List;

public class Commande {
    private int numCom;
    private String dateCom;
    private char enLigne;
    private char livraison;
    private Magasin magasin;
    private List<DetailCommande> detailsCommande;
    private Client client;

    public Commande(char enLigne, char livraison, Magasin magasin, Client client) {
        this.enLigne = enLigne;
        this.livraison = livraison;
        this.client = client;
        this.magasin = magasin;
        this.detailsCommande = new ArrayList<>();
    }

    public int getNumCom() {
        return numCom;
    }

    public String getDateCom() {
        return dateCom;
    }

    public char getEnLigne() {
        return enLigne;
    }

    public char getLivraison() {
        return livraison;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public List<DetailCommande> getDetailsCommande(){
        return this.detailsCommande;
    }
    
    public int size() {
        return this.detailsCommande.size();
    }
    
    public void ajouterDetailsCommande(Livre livre, int qte){
        this.detailsCommande.add(new DetailCommande(qte, livre.getPrix(), livre, numCom));
    }

    public double prixVente(Livre livre, Integer qte){
        double quantiteEnDouble = (double) qte;
        return livre.getPrix()*quantiteEnDouble;
    }

    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if(o == this){
            return true;
        }
        if(!(o instanceof Commande)){
            return false;
        }
        Commande tmp = (Commande) o;
        return this.numCom == tmp.numCom && this.dateCom.equals(tmp.dateCom) && this.enLigne == tmp.enLigne && this.livraison == tmp.livraison && this.magasin.equals(tmp.magasin) && this.client.equals(tmp.client) ;
    }

    @Override
    public String toString(){
        return "Commande N°"+this.numCom+" commandée le "+this.dateCom+", mode de réception: "+this.enLigne+" livraison: "+this.livraison+", numéro de client: "+this.client.getIdCli()+", numéro de magasin:  "+this.magasin.getIdMag();
    }



}
