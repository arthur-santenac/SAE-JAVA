import java.util.*;

public class Livre {
    
    private String isbn;

    private String titre;

    private int nbPages;

    private int datePubli;

    private double prix;

    private List<Auteur> auteurs;

    private List<Editeur> editeurs;

    private int qte;

    public Livre(String isbn, String titre, int nbPages, int datePubli, double prix){
        this.isbn = isbn;
        this.titre = titre;
        this.nbPages = nbPages;
        this.datePubli = datePubli;
        this.prix = prix;

    }

    public Livre(String isbn, String titre, int nbPages, int datePubli, double prix, int qte){
        this.isbn = isbn;
        this.titre = titre;
        this.nbPages = nbPages;
        this.datePubli = datePubli;
        this.prix = prix;
        this.qte = qte;

    }

    public String getIsbn(){
        return this.isbn;
    }

    public String getTitre(){
        return this.titre;
    }

    public int getNbPages(){
        return this.nbPages;
    }

    public int getDatePubli(){
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
        return this.isbn.equals(tmp.isbn) && this.titre.equals(tmp.titre) && this.nbPages== tmp.nbPages && this.datePubli == tmp.datePubli && this.prix == tmp.prix ;
    }

    @Override
    public String toString() {
        return this.titre + " " + this.datePubli + " " + this.prix + "€";
    }

    public String toStringVendeur() {
        return this.titre + " " + this.datePubli + " " + this.prix + "€; stock : " + this.qte +" exemplaires";
    }

}
