import java.util.ArrayList;
import java.util.List;

public class Commande {
    private int numCom;
    private String dateCom;
    private String enLigne;
    private String livraison;
    private Magasin magasin;
    private List<DetailCommande> detailsCommande;
    private Client client;

    public Commande(int numCom, String dateCom, String enLigne, String livraison, Magasin magasin,Client client) {
        this.numCom = numCom;
        this.dateCom = dateCom;
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

    public String getEnLigne() {
        return enLigne;
    }

    public String getLivraison() {
        return livraison;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public List<DetailCommande> getDetailsCommande(){
        return this.detailsCommande;
    }
    

    public void ajouterDetailsCommande(){
        int cpt = 1;
        for(Livre livre: this.client.getKey()){
            DetailCommande detail = new DetailCommande(cpt++, (int) this.client.getValue(livre), this.prixVente(livre, this.client.getValue(livre)), livre, this.getNumCom());
            this.detailsCommande.add(detail);
        }
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
        return this.numCom == tmp.numCom && this.dateCom.equals(tmp.dateCom) && this.enLigne.equals(tmp.enLigne) && this.livraison.equals(tmp.livraison) && this.magasin.equals(tmp.magasin) && this.client.equals(tmp.client) ;
    }

    @Override
    public String toString(){
        return "Commande N°"+this.numCom+" commandée le "+this.dateCom+", mode de réception: "+this.enLigne+" livraison: "+this.livraison+", numéro de client: "+this.client.getIdCli()+", numéro de magasin:  "+this.magasin.getIdMag();
    }



}
