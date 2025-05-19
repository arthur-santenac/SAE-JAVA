public class Client extends Personne{

    private int idCli;
    private Magasin magasin;

    public Client(String nom, String prenom, String adresse, int codePostal, String ville, int idCli) {
        super(nom, prenom, adresse, codePostal, ville);
        this.idCli = idCli;
        this.magasin = null;
    }

    public int getIdCli() {
        return idCli;
    }

    public void passerCommande(){

    }

    public void choixModeReception(){

    }

    public void choisirMagasin(Magasin mag){
        this.magasin = mag;
    }

    public void consulterCatalogue(){
        this.magasin.catalogue();
    }
}