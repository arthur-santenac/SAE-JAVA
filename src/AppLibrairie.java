import java.sql.SQLException;

public class AppLibrairie {

    public static boolean continuer = false;

    public AppLibrairie() {}

    public void run() {
        boolean quitter = false;
        while (!quitter) {
            try {
            connexionBD();
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
        catch (SQLException e){
            System.out.println("La connexion a échouée");
            run();
        }
        catch (ClassNotFoundException e){
            System.out.println("La classe n'existe pas");
            break;
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
        while (!continuer) {
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

    public void connexionBD() throws SQLException, ClassNotFoundException{

        ConnexionMySQL connexionMySQL= new ConnexionMySQL();
        MenuConnexionIdent();
        String identifiant = System.console().readLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        MenuConnexionMdp();
        String mdp = System.console().readLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        String serveur = "servinfo-maria";
        MenuConnexionNomBase();
        String nomBase = System.console().readLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();

        connexionMySQL.connecter(identifiant, mdp, serveur, nomBase);
	    if (connexionMySQL.isConnecte()){
		System.out.println("Vous etes connecté");
        } else {
            throw new SQLException();
        }
	

    }

    public void MenuConnexionIdent() {
        System.out.println("╭──────────────────────────╮");
        System.out.println("│  Connexion               │");
        System.out.println("├──────────────────────────┤");
        System.out.println("│ Entrez votre identifiant │");
        System.out.println("├──────────────────────────┤");
        System.out.println("│ Entrer \"q\" pour quitter  │");
        System.out.println("╰──────────────────────────╯");     
    }


        public void MenuConnexionNomBase() {
        System.out.println("╭───────────────────────────╮");
        System.out.println("│  Connexion                │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrez le nom de la BD    │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrer \"q\" pour quitter   │");
        System.out.println("╰───────────────────────────╯");     
    }

        public void MenuConnexionMdp() {
        System.out.println("╭───────────────────────────╮");
        System.out.println("│  Connexion                │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrez votre mot de passe │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrer \"q\" pour quitter   │");
        System.out.println("╰───────────────────────────╯");     
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
    
    public static void main(String[] args) {
        AppLibrairie app = new AppLibrairie();
        app.run();
    }

}








                                                                                                                                        