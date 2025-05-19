public class Executable {
    
    public static void main(String [] args){

        Client arthur = new Client("Santenac", "Arthur", "Rue des champs", 45000, "Orléans",1);
        System.out.println(arthur);

        Magasin auchan = new Magasin(1, "Auchan", "Saint-Jean de la ruelle");
        System.out.println(auchan);

        Livre lesMiserables = new Livre(1, "Les Misérables", 200, "31/03/1862", 11.90);
        Livre prince = new Livre(2, "Le Petit Prince", 185, "21/04/1943", 10.50);

        auchan.ajouterLivre(lesMiserables);
        auchan.ajouterLivre(prince);

        arthur.choisirMagasin(auchan);
        Commande commande = arthur.PasserUnecommande("Les Misérables", 2, ModeReception.ENLIGNE,auchan);
        auchan.ajouteCommande(commande);
        System.out.println(auchan.getCommandes());

        commande.ajouterDetailsCommande();
        System.out.println(commande.getDetailsCommande());
        


    }
}
