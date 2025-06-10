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

    public Client(String nom, String prenom, String adresse, int codePostal, String ville, int idCli) {
        super(nom, prenom, adresse, codePostal, ville);
        this.idCli = idCli;
        this.magasin = null;
    }

    public int getIdCli() {
        return idCli;
    }

    public String dateActuelle() {
       final Date date = new Date();
       return new SimpleDateFormat("dd-MM-yyyy").format(date);
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