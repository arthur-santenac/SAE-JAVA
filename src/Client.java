public class Client extends Personne{

    private int idCli;

    public Client(String nom, String prenom, String adresse, int codePostal, String ville, int idCli) {
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