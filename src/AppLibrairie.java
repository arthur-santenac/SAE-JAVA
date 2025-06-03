import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class AppLibrairie {

    public static boolean continuer = false;
    private ConnexionMySQL connexionMySQL;
    private Statement st;
    private MagasinBD magasinBD;
    private ClientBD clientBD;

    public AppLibrairie() {}

    public void run() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        boolean quitter = false;
        while (!quitter) {
            try {
                if (connexionBD()) {
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
                else {
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

        logo();
        menuConnexionIdent();
        String identifiant = System.console().readLine();
        identifiant = identifiant.strip();

        if (identifiant.equals("quitter") || identifiant.equals("q") || identifiant.equals("quit")) {return Compte.QUITTER;}

        System.out.print("\033[H\033[2J");
        System.out.flush();

        logo();
        menuConnexionMdp();
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
        try{
            String magasinChoisi = choisirMagasin();
            boolean commandeEnMagasin = choisirModeLivraison();
            String livraison = null;
            while (!continuer) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                logo();
                menuClient();
                String option = System.console().readLine();
                option = option.strip().toLowerCase();
            }
        } catch (SQLException e) {
            
        }
    }

    public String choisirMagasin() throws SQLException {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        List<String> listeMagasin = new ArrayList<>();
        st = connexionMySQL.createStatement();
		ResultSet set = st.executeQuery("select * from MAGASIN");
        while (set.next()) {
			listeMagasin.add(ljust(set.getString(2), 30) + " " + set.getString(3));
		}

        logo();
        menuChoisirMagasin(listeMagasin);
        String magasin = System.console().readLine();
        magasin = magasin.strip();
        try {
            if (Integer.parseInt(magasin) >= 1 && Integer.parseInt(magasin) <= listeMagasin.size()) {
                return magasin;
            } else {
                erreur();
                return choisirMagasin();
            }
        } catch (NumberFormatException e) {
            erreur();
            return choisirMagasin();
        }
    }

    public boolean choisirModeLivraison() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        logo();
        menuChoisirModeLivraison();
        String modeLivraison = System.console().readLine();
        modeLivraison = modeLivraison.strip();
        if (modeLivraison.equals("1")) {
            return true;
        } 
        else if (modeLivraison.equals("2")) {
            return false;
        }
        else {
            erreur();
            return choisirModeLivraison();
        }
    }

    public void runVendeur() {
        while (!continuer) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            logo();
            menuVendeur();
            String identifiant = System.console().readLine();
            identifiant = identifiant.strip().toLowerCase();
        }
    }

    public void runAdministrateur() {
        while (!continuer) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            logo();
            menuAdmin();
            String identifiant = System.console().readLine();
            identifiant = identifiant.strip().toLowerCase();
        }
    }

    public void erreur() {
        System.out.println("\n" + "Erreur veillez réessayer");
        System.console().readLine();
    }

    public boolean connexionBD() throws SQLException, ClassNotFoundException{

        ConnexionMySQL connexionMySQL = new ConnexionMySQL();
        menuConnexionIdentBD();
        String identifiant = System.console().readLine();
        if (identifiant.equals("quitter") || identifiant.equals("q") || identifiant.equals("quit")) {return false;}
        System.out.print("\033[H\033[2J");
        System.out.flush();
        menuConnexionMdpBD();
        String mdp = System.console().readLine();
        if (mdp.equals("quitter") || mdp.equals("q") || mdp.equals("quit")) {return false;}
        System.out.print("\033[H\033[2J");
        System.out.flush();
        String serveur = "servinfo-maria";
        String nomBase = "Librairie";

        connexionMySQL.connecter(identifiant, mdp, serveur, nomBase);
	    if (! connexionMySQL.isConnecte()){
            throw new SQLException();
        }
        this.connexionMySQL = connexionMySQL;
        return true;
	
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

    public void menuConnexionIdentBD() {
        System.out.println("╭──────────────────────────────────╮");
        System.out.println("│  Connexion BD                    │");
        System.out.println("├──────────────────────────────────┤");
        System.out.println("│ Entrez votre identifiant mariadb │");
        System.out.println("├──────────────────────────────────┤");
        System.out.println("│ Entrer \"q\" pour quitter          │");
        System.out.println("╰──────────────────────────────────╯");     
    }


    public void menuConnexionMdpBD() {

        System.out.println("╭───────────────────────────────────╮");
        System.out.println("│  Connexion BD                     │");
        System.out.println("├───────────────────────────────────┤");
        System.out.println("│ Entrez votre mot de passe mariadb │");
        System.out.println("├───────────────────────────────────┤");
        System.out.println("│ Entrer \"q\" pour quitter           │");
        System.out.println("╰───────────────────────────────────╯");     
    }

    public void menuClient() {
        System.out.println("     _______________________________________________________________   _______________________________________________________________  ");
        System.out.println(" .-/|                                                               \\ /                                                               |\\-.");
        System.out.println(" ||||                          CLIENT                                |                                                                ||||");
        System.out.println(" ||||________________________________________________________________|                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||   : Choisir un autre magasin                                   |                                                                ||||");
        System.out.println(" ||||   : Choisir un autre mode de réception                         |                                                                ||||");
        System.out.println(" ||||   : Passer une commande                                        |                                                                ||||");
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

    public void menuChoisirMagasin(List<String> listeMagasin) {
        System.out.println("     _______________________________________________________________   _______________________________________________________________  ");
        System.out.println(" .-/|                                                               \\ /                                                               |\\-.");
        System.out.println(" ||||                          CLIENT                                |                                                                ||||");
        System.out.println(" ||||________________________________________________________________|                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||  Veuillez entrer le nom du magasin dans lequel vous souhaiter  |                                                                ||||");
        System.out.println(" ||||  acheter un livre.                                             |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");

        for (int i=1;i<=listeMagasin.size();i++) {
            String ligne = " ||||   " + i + " - " + listeMagasin.get(i - 1);
            ligne = ljust(ligne, 69);
            System.out.println(ligne + "|                                                                ||||");
        }

        for (int i=0;i<32-listeMagasin.size();i++) {
            System.out.println(" ||||                                                                |                                                                ||||");
        }

        System.out.println(" ||/================================================================\\|/===============================================================|\\||");
        System.out.println(" '-----------------------------------------------------------------~___~----------------------------------------------------------------''");
    }

    public void menuChoisirModeLivraison() {
        System.out.println("     _______________________________________________________________   _______________________________________________________________  ");
        System.out.println(" .-/|                                                               \\ /                                                               |\\-.");
        System.out.println(" ||||                          CLIENT                                |                                                                ||||");
        System.out.println(" ||||________________________________________________________________|                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||  Veuillez choisir un mode de livraison                         |                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||   1 : En magasin                                               |                                                                ||||");
        System.out.println(" ||||   2 : En ligne                                                 |                                                                ||||");
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

    public void menuVendeur() {
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

    public void menuAdmin() {
        System.out.println("     _______________________________________________________________   _______________________________________________________________  ");
        System.out.println(" .-/|                                                               \\ /                                                               |\\-.");
        System.out.println(" ||||                    ADMINISTRATEUR                              |                                                                ||||");
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

    public void logo() {
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

    public static String ljust(String string, int longeur) {
        int aAjouter = longeur - string.length();
        if (aAjouter <= 0) return string;
        String padding = "";
        for (int i = 0; i < aAjouter; i++) {
            padding = padding + " ";
        }
        return string + padding;
    }
    
    public static void main(String[] args) {
        AppLibrairie app = new AppLibrairie();
        app.run();
    }

}








                                                                                                                                        