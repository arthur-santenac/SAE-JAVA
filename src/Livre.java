import java.util.*;

public class Livre {
    
    private int isbn;

    private String titre;

    private int nbPages;

    private String datePubli;

    private double prix;

    private List<Auteur> auteurs;

    private List<Editeur> editeurs;

    public Livre(int isbn, String titre, int nbPages, String datePubli, double prix){
        this.isbn = isbn;
        this.titre = titre;
        this.nbPages = nbPages;
        this.datePubli = datePubli;
        this.prix = prix;

    }

    public int getIsbn(){
        return this.isbn;
    }

    public String getTitre(){
        return this.titre;
    }

    public int getNbPages(){
        return this.nbPages;
    }

    public String getDatePubli(){
        return this.datePubli;
    }

    public double getPrix(){
        return this.prix;
    }

    public List<Auteur> getAuteurs(){
        return this.auteurs;
    }

    public List<Editeur> getEditeurs(){
        return this.editeurs;
    }

    public boolean ajouterAuteur(Auteur a){
        if(!this.auteurs.contains(a)){
            this.auteurs.add(a);
            return true;
        }
        return false;
    }


    public boolean ajouterEditeur(Editeur e){
        if(!this.editeurs.contains(e)){
            this.editeurs.add(e);
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if(o == this){
            return true;
        }
        if(!(o instanceof Livre)){
            return false;
        }
        Livre tmp = (Livre) o;
        return this.isbn == tmp.isbn && this.titre.equals(tmp.titre) && this.nbPages== tmp.nbPages && this.datePubli.equals(tmp.datePubli) && this.prix == tmp.prix ;
    }

}
