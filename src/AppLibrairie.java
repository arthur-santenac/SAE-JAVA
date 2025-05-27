import java.sql.SQLException;
import java.time.format.SignStyle;
import java.util.ArrayList;
import java.util.List;

public class AppLibrairie {

    public static boolean continuer = false;
    private ConnexionMySQL connexionMySQL;
    private MagasinBD magasinBD;
    private ClientBD clientBD;

    public AppLibrairie() {}

    public void run() {
        boolean quitter = false;
        while (!quitter) {
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
            case QUITTER:
                quitter = true;
            }
        }
        catch (MauvaisMotDePasseExeption e) {
            run();
        }
        }
        
    }

    public Compte Connexion() throws MauvaisMotDePasseExeption {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        Logo();
        MenuConnexionIdent();
        String identifiant = System.console().readLine();
        identifiant = identifiant.strip();

        if (identifiant.equals("quitter") || identifiant.equals("q") || identifiant.equals("quit")) {return Compte.QUITTER;}

        System.out.print("\033[H\033[2J");
        System.out.flush();

        Logo();
        MenuConnexionMdp();
        String mdp = System.console().readLine();
        mdp = mdp.strip();

        System.out.print("\033[H\033[2J");
        System.out.flush();
        if (mdp.equals("quitter") || mdp.equals("q") || mdp.equals("quit")) {return Compte.QUITTER;}
        if (identifiant.equals("client") && mdp.equals("client")) {return Compte.CLIENT;}
        else if (identifiant.equals("vendeur") && mdp.equals("vendeur")) {return Compte.VENDEUR;}
        else if (identifiant.equals("administrateur") && mdp.equals("administrateur")) {return Compte.ADMINISTRATEUR;}

        throw new MauvaisMotDePasseExeption();
    }

    public void runClient() {
        String magasinChoisi = null;
        String mode = null;
        String livraison = null;
        while (!continuer) {
            /* System.out.print("\033[H\033[2J");
            System.out.flush(); */
            /* menuClient();
            String option = System.console().readLine();
            option = option.strip().toUpperCase();
            if(option.equals("M")){
                List<String> listeMagasin = new ArrayList<>();
                try{
                    listeMagasin = this.magasinBD.listeDesNomDeMags();
                }
                catch(SQLException ex){
                    System.out.println("La liste de magasins n'a pas chargée.");
                }
                System.out.println(listeMagasin);
            }
            if(option.equals("C")){
                System.out.println("\n"+"╭───────────────────────────────────────────────────────────────────────────────────╮");
                System.out.println(     "│ Veuillez entrer le nom du magasin dans lequel vous souhaitez acheter des livres   │ ");
                System.out.println(     "╰───────────────────────────────────────────────────────────────────────────────────╯"+"\n");
                String magasinEntree = System.console().readLine();
                magasinChoisi = magasinEntree.strip();
                System.out.println("\n"+"╭─────────────────────────────────────────────────────────╮");
                System.out.println(     "│ Vous avez choisi de commander dans le magasin suivant : │  "+magasinChoisi);
                System.out.println(     "╰─────────────────────────────────────────────────────────╯"+"\n");
            }
            if(option.equals("R")){
                menuChoixModeRecep();
                String mode_brute = System.console().readLine();
                mode_brute = mode_brute.strip().toUpperCase();
                if(mode_brute.equals("O")){
                    mode = "O";
                    livraison = "C";
                    System.out.println("\n"+"Vous avez choisis en ligne."+"\n");
                }
                if(mode_brute.equals("N")){
                    mode = "N";
                    livraison = "M";
                    System.out.println("\n"+"Vous avez choisis en magasin."+"\n");
                }
            }
            if(option.equals("P")){
                if(magasinChoisi == null){
                    System.out.println("\n"+"Veuillez choisir un magasin avant de passer une commande."+"\n");
                    
                }
                else{
                    if(mode == null){
                        System.out.println("\n"+"Veuillez choisir un mode de reception avant de passer une commande."+"\n");
                    }
                    else{
                        System.out.println("\n"+"Veuillez entrer le nom du livre que vous "+"\n");
                        String nomLivre = System.console().readLine();
                        System.out.println("\n"+"Veuillez entrer le nombre d'exemplaire que vous souhaitez acheter."+"\n");
                        try{
                            String saisie = System.console().readLine();
                            Integer qte = Integer.parseInt(saisie);
                        }
                        catch(NumberFormatException e){
                            System.out.println("\n"+"Veuillez entrer un entier pour la quantité de livres."+"\n");
                        }

                        
                    }
                    
                }
                
            } */
            System.out.print("\033[H\033[2J");
            System.out.flush();
            Logo();
            MenuClient();
            String identifiant = System.console().readLine();
            identifiant = identifiant.strip().toLowerCase();
        }
        
    }

    public void runVendeur() {
        while (!continuer) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            Logo();
            MenuVendeur();
            String identifiant = System.console().readLine();
            identifiant = identifiant.strip().toLowerCase();
        }
    }

    public void runAdministrateur() {
        while (!continuer) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            Logo();
            MenuAdmin();
            String identifiant = System.console().readLine();
            identifiant = identifiant.strip().toLowerCase();
        }
    }

    public void Erreur() {
        System.out.println("\n" + "Erreur veillez réessayer");
        System.console().readLine();
    }

    public void menuConnexionIdent() {
        System.out.println("╭──────────────────────────╮");
        System.out.println("│  Connexion               │");
        System.out.println("├──────────────────────────┤");
        System.out.println("│ Entrez votre identifiant │");
        System.out.println("├──────────────────────────┤");
        System.out.println("│ Entrer \"q\" pour quitter  │");
        System.out.println("╰──────────────────────────╯");     
    }

    public void menuConnexionMdp() {
        System.out.println("╭───────────────────────────╮");
        System.out.println("│  Connexion                │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrez votre mot de passe │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrer \"q\" pour quitter   │");
        System.out.println("╰───────────────────────────╯");     
    }
        
    public void menuClient() {
        System.out.println("╭────────────────────────────╮");
        System.out.println("│         Menu client        │");
        System.out.println("├────────────────────────────┤");
        System.out.println("│ M : Afficher liste magasins│");
        System.out.println("│ C : Choisir un magasin     │");
        System.out.println("│ A : Afficher le catalgue   │");
        System.out.println("│ R : Choisir mode reception │");
        System.out.println("│ P : Passer une commande    │");
        System.out.println("╰────────────────────────────╯");
    }

    public void menuChoixModeRecep() {
        System.out.println("╭────────────────────────────╮");
        System.out.println("│         Menu client        │");
        System.out.println("├────────────────────────────┤");
        System.out.println("│ O : En ligne               │");
        System.out.println("│ N : En magasin             │");
        System.out.println("╰────────────────────────────╯");
    }

    public void menuVendeur() {
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

    public void menuAdmin() {
        System.out.println("╭────────────────────────────────────────╮");
        System.out.println("│  Menu administrateur                   │");
        System.out.println("├────────────────────────────────────────┤");
        System.out.println("│  : Créer un compte vendeur             │");
        System.out.println("│  : Ajouter une nouvelle librairie      │");
        System.out.println("│  : Gérer les stocks globaux            │");
        System.out.println("│  : Consulter les statistiques de vente │");
        System.out.println("╰────────────────────────────────────────╯");
    }
    public void MenuClient() {
        System.out.println("     _______________________________________________________________   _______________________________________________________________  ");
        System.out.println(" .-/|                                                               \\ /                                                               |\\-.");
        System.out.println(" ||||                          CLIENT                                |                                                                ||||");
        System.out.println(" ||||________________________________________________________________|                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||  : Passer une commande                                         |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||/================================================================\\|/===============================================================|\\||");
        System.out.println(" '-----------------------------------------------------------------~___~----------------------------------------------------------------''");
    }

    public void MenuVendeur() {
        System.out.println("     _______________________________________________________________   _______________________________________________________________  ");
        System.out.println(" .-/|                                                               \\ /                                                               |\\-.");
        System.out.println(" ||||                    VENDEUR                                     |                                                                ||||");
        System.out.println(" ||||________________________________________________________________|                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||  : Ajouter un livre                                            |                                                                ||||");
        System.out.println(" ||||  : Mettre à jour la quantité disponible d’un livre             |                                                                ||||");
        System.out.println(" ||||  : Vérifier la disponibilité d’un livre                        |                                                                ||||");
        System.out.println(" ||||  : Passer une commande pour un client en magasin               |                                                                ||||");
        System.out.println(" ||||  : Transférer un livre d’une autre librairie                   |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||/================================================================\\|/===============================================================|\\||");
        System.out.println(" '-----------------------------------------------------------------~___~----------------------------------------------------------------''");
    }

    public void MenuAdmin() {
        System.out.println("     _______________________________________________________________   _______________________________________________________________  ");
        System.out.println(" .-/|                                                               \\ /                                                               |\\-.");
        System.out.println(" ||||                    VENDEUR                                     |                                                                ||||");
        System.out.println(" ||||________________________________________________________________|                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||  : Créer un compte vendeur                                     |                                                                ||||");
        System.out.println(" ||||  : Ajouter une nouvelle librairie                              |                                                                ||||");
        System.out.println(" ||||  : Gérer les stocks globaux                                    |                                                                ||||");
        System.out.println(" ||||  : Consulter les statistiques de vente                         |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||/================================================================\\|/===============================================================|\\||");
        System.out.println(" '-----------------------------------------------------------------~___~----------------------------------------------------------------''");
    }

    public void Logo() {
        System.out.println("  ___       ___  ___      ___ ________  _______           _______      ___    ___ ________  ________  _______   ________   ________          ");
        System.out.println(" |\\  \\     |\\  \\|\\  \\    /  /|\\   __  \\|\\  ___ \\         |\\  ___ \\    |\\  \\  /  /|\\   __  \\|\\   __  \\|\\  ___ \\ |\\   ____\\ |\\   ____\\         ");
        System.out.println(" \\ \\  \\    \\ \\  \\ \\  \\  /  / | \\  \\|\\  \\ \\   __/|        \\ \\   __/|   \\ \\  \\/  / | \\  \\|\\  \\ \\  \\|\\  \\ \\   __/|\\ \\  \\___|_\\ \\  \\___|_        ");
        System.out.println("  \\ \\  \\    \\ \\  \\ \\  \\/  / / \\ \\   _  _\\ \\  \\_|/__       \\ \\  \\_|/__  \\ \\    / / \\ \\   ____\\ \\   _  _\\ \\  \\_|/_\\ \\_____  \\\\ \\_____  \\       ");
        System.out.println("   \\ \\  \\____\\ \\  \\ \\    / /   \\ \\  \\\\  \\\\ \\  \\_|\\ \\       \\ \\  \\_|\\ \\  /     \\/   \\ \\  \\___|\\ \\  \\\\  \\\\ \\  \\_|\\ \\|____|\\  \\\\|____|\\  \\      ");
        System.out.println("    \\ \\_______\\ \\__\\ \\__/ /     \\ \\__\\\\ _\\\\ \\_______\\       \\ \\_______\\/  /\\   \\    \\ \\__\\    \\ \\__\\\\ _\\\\ \\_______\\____\\_\\  \\ ____\\_\\  \\     ");
        System.out.println("     \\|_______|\\|__|\\|__|/       \\|__|\\|__|\\|_______|        \\|_______/__/ /\\ __\\    \\|__|     \\|__|\\|__|\\|_______|\\_________\\\\_________\\    ");
        System.out.println("                                                                      |__|/ \\|__|                                 \\|_________\\|_________|    ");
    }

    public ConnexionMySQL getConnexionMySQL() {
        return connexionMySQL;
    }

    public ClientBD getJoueurBD() {
        return clientBD;
    }

    public MagasinBD getFicheJoueur() {
        return magasinBD;
    }
    
    public static void main(String[] args) {
        AppLibrairie app = new AppLibrairie();
        app.run();
    }

}








                                                                                                                                        