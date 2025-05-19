public class Executable {
    
    public static void main(String [] args){

        Client arthur = new Client("Santenac", "Arthur", "Rue des champs", 45000, "Orléans",1);

        Magasin auchan = new Magasin(1, "Auchan", "Saint-Jean de la ruelle");

        Livre lesMiserables = new Livre(1, "Les Misérables", 200, "31/03/1862", 11.90);
        Livre prince = new Livre(2, "Le Petit Prince", 185, "21/04/1943", 10.50);
        


    }
}
