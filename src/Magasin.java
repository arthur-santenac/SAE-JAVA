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

    
/*     public List<Livre> catalogueFiltre(Filtre filtre)throws PasUnTelFiltreException{
        List<Livre> livresFiltrés = new ArrayList<>();
        switch (filtre) {
            case AUTEUR:
                for(Livre livre: this.livres){
                    if(livre.getAuteurs().contains())
                }
                break;
        
            default: throw new PasUnTelFiltreException();
                break;
        }
    } */
    

}
