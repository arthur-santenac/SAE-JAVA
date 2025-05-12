
public class Magasin {
    
    private int idMag;
    private String nomMag;
    private String villeMag;

    public Magasin(int idMag, String nomMag, String villeMag) {
        this.idMag = idMag;
        this.nomMag = nomMag;
        this.villeMag = villeMag;
    }

    public int getIdMag() {
        return idMag;
    }

    public void setIdMag(int idMag) {
        this.idMag = idMag;
    }

    public String getNomMag() {
        return nomMag;
    }

    public void setNomMag(String nomMag) {
        this.nomMag = nomMag;
    }

    public String getVilleMag() {
        return villeMag;
    }

    public void setVilleMag(String villeMag) {
        this.villeMag = villeMag;
    }

}
