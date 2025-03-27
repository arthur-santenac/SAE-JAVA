public class Livre {
    
    private int isbn;

    private String titre;

    private int nbPages;

    private String datePubli;

    private double prix;

    public Livre(int isbn, String titre, int nbPages, String datePubli, double prix){
        this.isbn = isbn;
        this.titre = titre;
        this.nbPages = nbPages;
        this.datePubli = datePubli;
        this.prix = prix;
    }

    public int getIdLivre(){
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
}
