public class Auteur {
    
    private int idAuteur;

    private String nomAuteur;

    public Auteur(int id, String nom){
        this.idAuteur = id;
        this.nomAuteur = nom;
    }

    public int getIdAuteur(){
        return this.idAuteur;
    }

    public String getNomAuteur(){
        return this.nomAuteur;
    }
}
