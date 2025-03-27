public class Client extends Personne{

    private int idCli;

    public Client(int idCli){
        super(String nomCli, String prenomCli, String adressCli, int codePostal, String villeCli);
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