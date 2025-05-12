public class Auteur {
    
    private int idAuteur;

    private String nomAuteur;

    private String anneNaiss;

    private String anneDeces;

    public Auteur(int id, String nom,String anneNaiss, String anneDeces){
        this.idAuteur = id;
        this.nomAuteur = nom;
        this.anneNaiss = anneNaiss;
        this.anneDeces = anneDeces;
    }

    public int getIdAuteur(){
        return this.idAuteur;
    }

    public String getNomAuteur(){
        return this.nomAuteur;
    }

    public String getAnneNaiss(){
        return this.anneNaiss;
    }

    public String getAnneDeces(){
        return this.anneDeces;
    }


}
