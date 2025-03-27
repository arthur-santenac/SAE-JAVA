public class Client extends Personne{

    private int idCli;

    public Client(String nom, String prenom, String adresse, String codePostal, String ville, int idCli) {
        super(nom, prenom, adresse, codePostal, ville);
        this.idCli = idCli;
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