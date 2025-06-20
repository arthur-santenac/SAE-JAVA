import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Magasin {
    
    private int idMag;
    private String nomMag;
    private String villeMag;
    private List<Commande> commandes;
    private List<Livre> livres;

    public Magasin(int idMag, String nomMag, String villeMag) {
        this.idMag = idMag;
        this.nomMag = nomMag;
        this.villeMag = villeMag;
        this.commandes = new ArrayList<>();
        this.livres = new ArrayList<>();
    }

    public int getIdMag() {
        return this.idMag;
    }

    public void setIdMag(int idMag) {
        this.idMag = idMag;
    }

    public String getNomMag() {
        return this.nomMag;
    }

    public void setNomMag(String nomMag) {
        this.nomMag = nomMag;
    }

    public String getVilleMag() {
        return this.villeMag;
    }



    public boolean ajouteCommande(Commande c){
        if(c.getMagasin().getNomMag().equals(this.nomMag)){
            if(!this.commandes.contains(c)){
                this.commandes.add(c);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean ajouterLivre(Livre l){
        if(!this.livres.contains(l)){
            this.livres.add(l);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Commande> getCommandes(){
        return this.commandes;
    }

    public List<Livre> getLivres(){
        return this.livres;
    }

    public List<Livre> catalogue(){
        return this.getLivres();
    }

    
    
    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if(o == this){
            return true;
        }
        if(!(o instanceof Magasin)){
            return false;
        }
        Magasin tmp = (Magasin) o;
        return this.idMag == tmp.idMag && this.nomMag.equals(tmp.nomMag) && this.villeMag.equals(tmp.villeMag);
    }

    @Override
    public String toString(){
        return nomMag + " " + this.villeMag;
    }

}
