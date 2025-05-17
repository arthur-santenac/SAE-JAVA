
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

    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if(o == this){
            return true;
        }
        if(!(o instanceof Editeur)){
            return false;
        }
        Editeur tmp = (Editeur) o;
        return this.idEditeur == tmp.idEditeur && this.nomEditeur.equals(tmp.nomEditeur);
    }
    
    
}
