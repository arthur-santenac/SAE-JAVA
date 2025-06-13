import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Client {

    private int idCli;
    private String nom;
    private String prenom;
    private String adresse;
    private String codePostal;
    private String ville;

    public Client(String nom, String prenom, String adresse, String codePostal, String ville, int idCli) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.idCli = idCli;
    }

    public int getIdCli() {
        return idCli;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getVille() {
        return ville;
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