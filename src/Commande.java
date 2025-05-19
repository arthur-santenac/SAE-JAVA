import java.util.ArrayList;
import java.util.List;

public class Commande {

    private int numCom;
    private String dateCom;
    private String enLigne;
    private String livraison;
    private Magasin mag;
    private List<DetailCommande> detailsCom;
    private Client client;

    public Commande(int numCom, String dateCom, String enLigne, String livraison, Magasin mag, Client client){
        this.numCom = numCom;
        this.dateCom = dateCom;
        this.enLigne = enLigne;
        this.livraison = livraison;
        this.mag = mag;
        this.detailsCom = new ArrayList<>();
        this.client = client;
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

    public Magasin getMagasin(){
        return this.mag;
    }

    public List<DetailCommande> getDetailsCommande(){
        return this.detailsCom;
    }
    

    public void ajouterDetailsCommande(){
        int cpt = 1;
        for(Livre livre: this.client.getKey()){
            DetailCommande detail = new DetailCommande(cpt++, (int) this.client.getValue(livre), this.prixVente(livre, this.client.getValue(livre)), livre, this.getNumCom());
            this.detailsCom.add(detail);
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
        return this.numCom == tmp.numCom && this.dateCom.equals(tmp.dateCom) && this.enLigne.equals(tmp.enLigne) && this.livraison.equals(tmp.livraison) && this.mag.equals(tmp.mag) && this.client.equals(tmp.client) ;
    }

    @Override
    public String toString(){
        return "Commande N°"+this.numCom+" commandée le "+this.dateCom+", mode de réception: "+this.enLigne+" livraison: "+this.livraison+", numéro de client: "+this.client.getIdCli()+", numéro de magasin:  "+this.mag.getIdMag();
    }
}
