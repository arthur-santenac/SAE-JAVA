package AppLibrairie;
public class Editeur {
    
    private int idEditeur;

    private String nomEditeur;

    public Editeur(int id, String nom){
        this.idEditeur = id;
        this.nomEditeur = nom;
    }


    public int getIdEditeur(){
        return this.idEditeur;
    }

    public String getNomEditeur(){
        return this.nomEditeur;
    }
    
    
}
