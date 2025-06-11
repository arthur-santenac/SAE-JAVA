
public class Auteur {

    private int idAuteur;
    private String nomAuteur;
    private String anneNaiss;
    private String anneDeces;

    public Auteur(int id, String nom, String anneNaiss, String anneDeces) {
        this.idAuteur = id;
        this.nomAuteur = nom;
        this.anneNaiss = anneNaiss;
        this.anneDeces = anneDeces;
    }

    public int getIdAuteur() {
        return this.idAuteur;
    }

    public String getNomAuteur() {
        return this.nomAuteur;
    }

    public String getAnneNaiss() {
        return this.anneNaiss;
    }

    public String getAnneDeces() {
        return this.anneDeces;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof Auteur)) {
            return false;
        }
        Auteur tmp = (Auteur) o;
        return this.idAuteur == tmp.idAuteur && this.nomAuteur.equals(tmp.nomAuteur)
                && this.anneNaiss.equals(tmp.anneNaiss) && this.anneDeces.equals(tmp.anneDeces);
    }

}
