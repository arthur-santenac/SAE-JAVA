public class AppLibrairie {

    public void Client() {
        System.out.println("╭────────────────────────────╮");
        System.out.println("│  Menu client               │");
        System.out.println("├────────────────────────────┤");
        System.out.println("│  : Passer une commande     │");
        System.out.println("╰────────────────────────────╯");
    }

    public void Vendeur() {
        System.out.println("╭────────────────────────────────────────────────────╮");
        System.out.println("│  Menu vendeur                                      │");
        System.out.println("├────────────────────────────────────────────────────┤");
        System.out.println("│  : Ajouter un livre                                │");
        System.out.println("│  : Mettre à jour la quantité disponible d’un livre │");
        System.out.println("│  : Vérifier la disponibilité d’un livre            │");
        System.out.println("│  : Passer une commande pour un client en magasin   │");
        System.out.println("│  : Transférer un livre d’une autre librairie       │");
        System.out.println("╰────────────────────────────────────────────────────╯");
    }

    public void Admin() {
        System.out.println("╭────────────────────────────────────────╮");
        System.out.println("│  Menu administrateur                   │");
        System.out.println("├────────────────────────────────────────┤");
        System.out.println("│  : Créer un compte vendeur             │");
        System.out.println("│  : Ajouter une nouvelle librairie      │");
        System.out.println("│  : Gérer les stocks globaux            │");
        System.out.println("│  : Consulter les statistiques de vente │");
        System.out.println("╰────────────────────────────────────────╯");
    }


}