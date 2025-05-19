import java.util.HashMap;
import java.util.Map;

public class Client extends Personne{

    private int idCli;
    private Map<Livre, Integer> panier;

    public Client(String nom, String prenom, String adresse, int codePostal, String ville, int idCli) {
        super(nom, prenom, adresse, codePostal, ville);
        this.idCli = idCli;
        this.panier= new HashMap<>();
    }

    public int getIdCli() {
        return idCli;
    }

    public void passerCommande(){
       
    }

    public void choixModeReception(){

    }

    public void consulterCatalogue(){
        
    }
}