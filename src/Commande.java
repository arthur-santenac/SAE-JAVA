import java.util.ArrayList;
import java.util.List;

public class Commande {
    private int numCom;
    private String dateCom;
    private String enLigne;
    private String livraison;
    private Magasin magasin;
    private List<DetailCommande> detailsCommande;

    public Commande(int numCom, String dateCom, String enLigne, String livraison, Magasin magasin) {
        this.numCom = numCom;
        this.dateCom = dateCom;
        this.enLigne = enLigne;
        this.livraison = livraison;
        this.magasin = magasin;
        this.detailsCommande = new ArrayList<>();
    }

    public int getNumCom() {
        return numCom;
    }

    public String getDateCom() {
        return dateCom;
    }

    public String getEnLigne() {
        return enLigne;
    }

    public String getLivraison() {
        return livraison;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public List<DetailCommande> getDetailsCommande() {
        return detailsCommande;
    }


}
