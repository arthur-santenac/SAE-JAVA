import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Client extends Personne{

    private int idCli;
    private Magasin magasin;
    private Map<Livre, Integer> panier;
    private ModeReception mode;

    public Client(String nom, String prenom, String adresse, int codePostal, String ville, int idCli) {
        super(nom, prenom, adresse, codePostal, ville);
        this.idCli = idCli;
        this.magasin = null;
        this.panier= new HashMap<>();
        this.mode = null;
    }

    public int getIdCli() {
        return idCli;
    }

    public Set<Livre> getKey(){
        return this.panier.keySet();
    }

    public Integer getValue(Livre livre){
        return this.panier.get(livre);
    }

    public Commande PasserUnecommande(String nomLivre,Integer qteChoisit,ModeReception modeChoisit,Magasin mag){
        this.magasin = mag;
        this.mode = modeChoisit;
        if(this.magasin.getCommandes().isEmpty()){
            for(Livre livre: this.magasin.getLivres()){
                if(livre.getTitre().equals(nomLivre)){
                    Livre l = new Livre(livre.getIsbn(), nomLivre, livre.getNbPages(), livre.getDatePubli(), livre.getPrix(), 0, "nomClass");
                    int numCom = 0;
                    Commande commande = new Commande(numCom, this.dateActuelle(), this.choixModeReception(this.mode), this.livraison(mode), this.magasin,this);
                    this.panier.put(l,qteChoisit);
                    return commande;
                }
            }
        }
        else{
            for(Livre livre: this.magasin.getLivres()){
                if(livre.getTitre().equals(nomLivre)){
                    Livre l = new Livre(livre.getIsbn(), nomLivre, livre.getNbPages(), livre.getDatePubli(), livre.getPrix(), 0, "nomClass");
                    int numCom = this.magasin.getCommandes().size()+1;
                    Commande commande = new Commande(numCom, this.dateActuelle(), this.choixModeReception(this.mode), this.livraison(mode), this.magasin,this);
                    this.panier.put(l,qteChoisit);
                    return commande;
                }
            }  
        }
        return null;
    }


    private String choixModeReception(ModeReception mode){
        String res;
        switch (mode) {
            case ENLIGNE: res ="O"; break;
            case MAGASIN: res = "N"; break;
            default: res = "O"; break;
        }
        return res;
    }

    private String dateActuelle() {
       final Date date = new Date();
       return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    private String livraison(ModeReception mode){
        String res;
        switch (mode) {
            case ENLIGNE: res ="C"; break;
            case MAGASIN: res ="M";break;
            default: res = "M";break;
        }
        return res;
    }



    public void choisirMagasin(Magasin mag){
        this.magasin = mag;
    }

    public void consulterCatalogue(){
        this.magasin.catalogue();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        Client tmp = (Client) o;
    
        return this.idCli == tmp.idCli
            && this.getCodePostal() == tmp.getCodePostal()
            && this.getNom().equals(tmp.getNom())
            && this.getPrenom().equals(tmp.getPrenom())
            && this.getAdresse().equals(tmp.getAdresse())
            && this.getVille().equals(tmp.getVille())
            && (this.magasin == null ? tmp.magasin == null : this.magasin.equals(tmp.magasin))
            && (this.panier == null ? tmp.panier == null : this.panier.equals(tmp.panier))
            && (this.mode == null ? tmp.mode == null : this.mode.equals(tmp.mode));
    }

    @Override
    public String toString(){
        return this.idCli+" : "+super.toString();
    }
    
}