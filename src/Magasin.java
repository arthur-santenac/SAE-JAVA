import java.util.ArrayList;
import java.util.List;

public class Magasin {
    
    private int idMag;
    private String nomMag;
    private String villeMag;
    private List<Commande> commandes;

    public Magasin(int idMag, String nomMag, String villeMag) {
        this.idMag = idMag;
        this.nomMag = nomMag;
        this.villeMag = villeMag;
        this.commandes = new ArrayList<>();
    }

    public int getIdMag() {
        return idMag;
    }

    public void setIdMag(int idMag) {
        this.idMag = idMag;
    }

    public String getNomMag() {
        return nomMag;
    }

    public void setNomMag(String nomMag) {
        this.nomMag = nomMag;
    }

    public String getVilleMag() {
        return villeMag;
    }

    public void setVilleMag(String villeMag) {
        this.villeMag = villeMag;
    }

    public boolean ajouteCommande(Commande c){
        if(c.getMagasin().getNomMag().equals(this.nomMag)){
            this.commandes.add(c);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Commande> getCommandes(){
        return this.commandes;
    }

}
