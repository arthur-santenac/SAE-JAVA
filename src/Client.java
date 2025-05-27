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

    public Client(String nom, String prenom, String adresse, int codePostal, String ville, int idCli) {
        super(nom, prenom, adresse, codePostal, ville);
        this.idCli = idCli;
        this.magasin = null;
        this.panier= new HashMap<>();
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

    public Commande PasserUnecommande(String nomLivre,Integer qteChoisit,String modeChoisit,Magasin mag){
        this.magasin = mag;
        if(this.magasin.getCommandes().isEmpty()){
            for(Livre livre: this.magasin.getLivres()){
                if(livre.getTitre().equals(nomLivre)){
                    Livre l = new Livre(livre.getIsbn(), nomLivre, livre.getNbPages(), livre.getDatePubli(), livre.getPrix(), 0, "nomClass");
                    int numCom = 0;
                    Commande commande = new Commande(numCom, this.dateActuelle(), modeChoisit, this.livraison(modeChoisit), this.magasin,this);
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
                    Commande commande = new Commande(numCom, this.dateActuelle(), modeChoisit, this.livraison(modeChoisit), this.magasin,this);
                    this.panier.put(l,qteChoisit);
                    return commande;
                }
            }  
        }
        return null;
    }


    public String dateActuelle() {
       final Date date = new Date();
       return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public String livraison(String mode){
        if(mode.equals("O")){
            return "C";
        }
        else{
            return "M";
        }
       
    }



    public void choisirMagasin(Magasin mag){
        this.magasin = mag;
    }

    public void consulterCatalogue(){
        this.magasin.catalogue();
    }


    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if(o == this){
            return true;
        }
        if(!(o instanceof Client)){
            return false;
        }
        Client tmp = (Client) o;
        return this.idCli == tmp.idCli;
    }


}