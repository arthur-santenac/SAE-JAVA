import java.time.format.SignStyle;
import java.util.ArrayList;
import java.util.List;

public class AppLibrairie {

    public enum Compte {
        CLIENT, VENDEUR, ADMINISTRATEUR
    }

    public static boolean continuer = false;

    public AppLibrairie() {}

    public void run() {
        try {
            Compte compte = Connexion();
            switch (compte) {
            case CLIENT:
                runClient();
                break;
            case VENDEUR:
                runVendeur();
                break;
            case ADMINISTRATEUR:
                runAdministrateur();
                break;
            }
        }
        catch (MauvaisMotDePasseExeption e) {
            run();
        }
    }

    public Compte Connexion() throws MauvaisMotDePasseExeption {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        MenuConnexionIdent();
        String identifiant = System.console().readLine();
        identifiant = identifiant.strip();

        System.out.print("\033[H\033[2J");
        System.out.flush();

        MenuConnexionMdp();
        String mdp = System.console().readLine();
        mdp = mdp.strip();

        System.out.print("\033[H\033[2J");
        System.out.flush();

        if (identifiant.equals("client") && mdp.equals("client")) {return Compte.CLIENT;}
        else if (identifiant.equals("vendeur") && mdp.equals("vendeur")) {return Compte.VENDEUR;}
        else if (identifiant.equals("administrateur") && mdp.equals("administrateur")) {return Compte.ADMINISTRATEUR;}

        throw new MauvaisMotDePasseExeption();
    }

    public void runClient() {
        while (!continuer) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            MenuClient();
            String option = System.console().readLine();
            option = option.strip().toUpperCase();
            String magasinChoisi = null;
            String mode = null;
            if(option.equals("M")){
                List<String> listeMagasin = new ArrayList<>();
                System.out.println(listeMagasin);
                // utiliser une méthode implémenter dans MagasinBD
            }
            else if(option.equals("C")){
                System.out.println("Veuillez entrer le nom du magasin dnas lequel vous souhaitez acheter des livres");
                magasinChoisi = System.console().readLine();
                magasinChoisi = magasinChoisi.strip().toLowerCase();
                System.out.println("Vous avez choisi de commander dans le magasin : "+magasinChoisi);
            }
            else if(option.equals("R")){
                System.out.println("Veuillez choisir le mode de réception de vos futur achats: "+"\n"+" - En Ligne"+"\n"+" - Magasin");
                String mode_brute = System.console().readLine();
                mode_brute.strip().toLowerCase();
                if(mode_brute.equals("enligne")){
                    mode = "0";
                }
                if(mode_brute.equals("magasin")){
                    mode = "N";
                }
            }
            else if(option.equals("P"));
        }
        
    }

    public void runVendeur() {
        while (!continuer) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            MenuVendeur();
            String identifiant = System.console().readLine();
            identifiant = identifiant.strip().toLowerCase();
        }
    }

    public void runAdministrateur() {
        while (!continuer) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            MenuAdmin();
            String identifiant = System.console().readLine();
            identifiant = identifiant.strip().toLowerCase();
        }
    }

    public void Erreur() {
        System.out.println("\n" + "Erreur veillez réessayer");
        System.console().readLine();
    }

    public void MenuConnexionIdent() {
        System.out.println("╭──────────────────────────╮");
        System.out.println("│  Connexion               │");
        System.out.println("├──────────────────────────┤");
        System.out.println("│ Entrez votre identifiant │");
        System.out.println("╰──────────────────────────╯");     
    }

    public void MenuConnexionMdp() {
        System.out.println("╭───────────────────────────╮");
        System.out.println("│  Connexion                │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrez votre mot de passe │");
        System.out.println("╰───────────────────────────╯");     
    }
        
    public void MenuClient() {
        System.out.println("╭────────────────────────────╮");
        System.out.println("│  Menu client               │");
        System.out.println("├────────────────────────────┤");
        System.out.println("│ M : Afficher liste magasins│");
        System.out.println("│ C : Choisir un magasin     │");
        System.out.println("│ A : Afficher le catalgue   │");
        System.out.println("│ R : Choisir mode reception │");
        System.out.println("│ P : Passer une commande    │");
        System.out.println("╰────────────────────────────╯");
    }

    public void MenuVendeur() {
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

    public void MenuAdmin() {
        System.out.println("╭────────────────────────────────────────╮");
        System.out.println("│  Menu administrateur                   │");
        System.out.println("├────────────────────────────────────────┤");
        System.out.println("│  : Créer un compte vendeur             │");
        System.out.println("│  : Ajouter une nouvelle librairie      │");
        System.out.println("│  : Gérer les stocks globaux            │");
        System.out.println("│  : Consulter les statistiques de vente │");
        System.out.println("╰────────────────────────────────────────╯");
    }
    
    public static void main(String[] args) {
        AppLibrairie app = new AppLibrairie();
        app.run();
    }

}