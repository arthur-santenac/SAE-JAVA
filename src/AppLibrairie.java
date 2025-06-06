import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AppLibrairie {

    public static boolean continuer = false;
    private ConnexionMySQL connexionMySQL = null;
    private Statement st;
    private MagasinBD magasinBD;
    private Client utilisateur;
    private ClientBD clientBD;

    public AppLibrairie() {
        
    }

    public void run() {
        clear();

        if (connexionMySQL == null) {
            try {
                if (!connexionBD()) {
                    return;
                }

            } catch (SQLException e) {
                System.out.println("La connexion a échouée");
                System.console().readLine();
                run();
            }
            catch (ClassNotFoundException e) {
                System.out.println("La connexion a échouée");
                System.console().readLine();
                return;
            }
        }
        
        this.clientBD = new ClientBD(connexionMySQL);

        menuChoisirCreerOuConnecter();
        String connectionOuCreer = System.console().readLine();
        connectionOuCreer = connectionOuCreer.strip();
        if (connectionOuCreer.equals("1")) {
            clear();
            while (!AppLibrairie.continuer) {
                try {
                    String compte = Connexion();
                    switch (compte) {
                        case "client":
                            runClient();
                            break;
                        case "vendeur":
                            runVendeur();
                            break;
                        case "administrateur":
                            runAdministrateur();
                            break;
                        case "quitter":
                            AppLibrairie.continuer = true;
                    }
                }
                catch (MauvaisMotDePasseExeption e) {
                    System.out.println("Mauvais identifiants");
                    erreur();
                    run();
                }
                catch (SQLException e){
                    System.out.println("La connexion a échouée");
                    erreur();
                    run();
                }
            }
        }
        else if (connectionOuCreer.equals("2")) {
            try {
                creerUnCompte();
                run();
            } catch (SQLException e) {
                System.out.println("La création a échouée");
            }
        }
        else if (connectionOuCreer.equals("quitter") || connectionOuCreer.equals("q") || connectionOuCreer.equals("quit")) {}
        else {
            erreur();
            run();   
        }
        clear();
    }

    public String Connexion() throws MauvaisMotDePasseExeption, SQLException{

        clear();

        logo();
        menuConnexionEmail();
        String email = System.console().readLine();
        email = email.strip();

        if (email.equals("quitter") || email.equals("q") || email.equals("quit")) {return "quitter";}

        clear();

        logo();
        menuConnexionMdp();
        String mdp = System.console().readLine();
        mdp = mdp.strip();

        clear();
        if (mdp.equals("quitter") || mdp.equals("q") || mdp.equals("quit")) {return "quitter";}

        st = connexionMySQL.createStatement();
		ResultSet set = st.executeQuery("select * from CONNEXION natural join CLIENT");

        while (set.next()) {
			if (email.equals(set.getString(2)) && mdp.equals(set.getString(3))) {
                this.utilisateur = new Client(set.getString(5), set.getString(6), set.getString(7), set.getInt(9), set.getString(8), set.getInt(1));
                return set.getString(4);
            }
		}

        throw new MauvaisMotDePasseExeption();
    }

    public void creerUnCompte() throws SQLException{

        clear();

        creerCompteEmail();
        String email = System.console().readLine();
        email = email.strip();
        if (email.equals("quitter") || email.equals("q") || email.equals("quit")) {return;}

        clear();

        creerCompteMdp();
        String mdp = System.console().readLine();
        mdp = mdp.strip();
        if (mdp.equals("quitter") || mdp.equals("q") || mdp.equals("quit")) {return;}

        clear();

        creerCompteNom();
        String nom = System.console().readLine();
        nom = nom.strip();
        if (nom.equals("quitter") || nom.equals("q") || nom.equals("quit")) {return;}

        clear();

        creerComptePrenom();
        String prenom = System.console().readLine();
        prenom = prenom.strip();
        if (prenom.equals("quitter") || prenom.equals("q") || prenom.equals("quit")) {return;}

        clear();

        creerCompteAdresse();
        String adresse = System.console().readLine();
        adresse = adresse.strip();
        if (adresse.equals("quitter") || adresse.equals("q") || adresse.equals("quit")) {return;}

        clear();

        creerCompteVille();
        String ville = System.console().readLine();
        ville = ville.strip();
        if (ville.equals("quitter") || ville.equals("q") || ville.equals("quit")) {return;}

        clear();

        creerCompteCodePostal();
        String codePostal = System.console().readLine();
        codePostal = codePostal.strip();
        if (codePostal.equals("quitter") || codePostal.equals("q") || codePostal.equals("quit")) {return;}
        int codePostalInt = Integer.parseInt(codePostal);

        clear();

        int idcli = this.clientBD.maxNum();

        PreparedStatement ps = connexionMySQL.prepareStatement("insert into CLIENT values (?,?,?,?,?,?)");
		ps.setInt(1, idcli);
		ps.setString(2, nom);
		ps.setString(3, prenom);
        ps.setString(4, adresse);
        ps.setString(5, ville);
        ps.setInt(6, codePostalInt);
		ps.executeUpdate();

        ps = connexionMySQL.prepareStatement("insert into CONNEXION values (?,?,?, ?)");
		ps.setString(1, email);
		ps.setString(2, mdp);
        ps.setInt(3, idcli);
		ps.setString(4, "client");
		ps.executeUpdate();
    }

    public void runClient() {
        try{
            String magasin = choisirMagasin();
            boolean enLigne = choisirModeLivraison();
            String livraison = null;
            while (!AppLibrairie.continuer) {
                clear();
                logo();
                menuClient();
                String option = System.console().readLine();
                option = option.strip().toLowerCase();
                if (option.equals("1")) {choisirMagasin();}
                else if (option.equals("2")) {choisirModeLivraison();}
                else if (option.equals("3")) {commander(magasin, enLigne);}
                else if (option.equals("4")) {run();}
                else if (option.equals("5") || option.equals("quitter") || option.equals("q") || option.equals("quit")) {AppLibrairie.continuer = true;}
                else {
                    erreur();
                }
            }
        } catch (SQLException e) {
        
        }
    }

    public String choisirMagasin() throws SQLException {
        clear();

        List<String> listeMagasin = new ArrayList<>();
        st = connexionMySQL.createStatement();
		ResultSet set = st.executeQuery("select * from MAGASIN");
        while (set.next()) {
			listeMagasin.add(ljust(set.getString(2), 30) + " " + set.getString(3));
		}

        logo();
        menuChoisirMagasin(listeMagasin);
        String magasin = System.console().readLine();
        if(magasin.equals("1")){
            try{
                System.out.println(this.magasinBD.listeDesMagasins());
            }
            catch(SQLException ex){
                System.out.println("La liste de magasins est vide");
            }
        }
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
        clear();

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

    public void commander(String magasin, boolean enLigne) {
        clear();
        logo();
        menuCommander();
        String commander = System.console().readLine();
        commander = commander.strip();
        if (commander.equals("1")) {
            try {
                chercherLivre(magasin, enLigne);
                commander(magasin, enLigne);
            } catch (SQLException e) {

            }
            
        }
        else if (commander.equals("2")) {
            
            commander(magasin, enLigne);
        }
        else if (commander.equals("3")) {
            meilleursVentes(magasin, enLigne);
            commander(magasin, enLigne);
        }
        else if (commander.equals("4") || commander.equals("quitter") || commander.equals("q") || commander.equals("quit")) {}
        else {
            erreur();
            commander(magasin, enLigne);
        }
    }

    public void chercherLivre(String magasin, boolean enLigne) throws SQLException{
        clear();
        logo();
        menuChercherLivre();
        String chercher = System.console().readLine();
        chercher = chercher.strip();
        if (chercher.equals("q") || chercher.equals("quit") || chercher.equals("quitter")) {return;}
        boolean trouver = false;
        Map<Integer, String> mapLivre = new HashMap<>();
        st = connexionMySQL.createStatement();
		ResultSet set = st.executeQuery("select isbn, titre from LIVRE natural join MAGASIN where nommag = " + magasin);
        while (set.next()) {
			if (set.getString(2).equals(chercher)) {
                mapLivre.put(set.getInt(1), set.getString(2));
                trouver = true;
            }
		}
        if (!trouver) {
            System.out.println("le livre n'existe pas");
            erreur();
            chercherLivre(magasin, enLigne);
        }
        
    }

    public void meilleursVentes(String magasin, boolean enLigne) {

    }

    public void runVendeur() {
        while (!AppLibrairie.continuer) {
            clear();
            logo();
            menuVendeur();
            String identifiant = System.console().readLine();
            identifiant = identifiant.strip().toLowerCase();
        }
    }

    public void runAdministrateur() {
        while (!AppLibrairie.continuer) {
            clear();
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
        clear();
        menuConnexionMdpBD();
        String mdp = System.console().readLine();
        if (mdp.equals("quitter") || mdp.equals("q") || mdp.equals("quit")) {return false;}
        clear();
        String serveur = "servinfo-maria";
        String nomBase = "DB" + identifiant;

        connexionMySQL.connecter(identifiant, mdp, serveur, nomBase);
	    if (! connexionMySQL.isConnecte()){
            throw new SQLException();
        }
        this.connexionMySQL = connexionMySQL;
        return true;
	
    }

    public void menuChoisirCreerOuConnecter() {
        System.out.println("╭──────────────────────────╮");
        System.out.println("│ 1 - Se Connecter         │");
        System.out.println("│ 2 - Créer un compte      │");
        System.out.println("│ Entrer \"q\" pour quitter  │");
        System.out.println("╰──────────────────────────╯");     
    }

    public void creerCompteEmail() {
        System.out.println("╭────────────────────────────╮");
        System.out.println("│  Créer un compte           │");
        System.out.println("├────────────────────────────┤");
        System.out.println("│ Entrez votre adresse email │");
        System.out.println("├────────────────────────────┤");
        System.out.println("│ Entrer \"q\" pour quitter    │");
        System.out.println("╰────────────────────────────╯");     
    }

    public void creerCompteMdp() {
        System.out.println("╭───────────────────────────╮");
        System.out.println("│  Créer un compte          │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrez un mot de passe    │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrer \"q\" pour quitter   │");
        System.out.println("╰───────────────────────────╯");     
    }

        public void creerCompteNom() {
        System.out.println("╭───────────────────────────╮");
        System.out.println("│  Créer un compte          │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrez votre nom          │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrer \"q\" pour quitter   │");
        System.out.println("╰───────────────────────────╯");     
    }

    public void creerComptePrenom() {
        System.out.println("╭───────────────────────────╮");
        System.out.println("│  Créer un compte          │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrez votre prenom       │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrer \"q\" pour quitter   │");
        System.out.println("╰───────────────────────────╯");     
    }

        public void creerCompteAdresse() {
        System.out.println("╭───────────────────────────╮");
        System.out.println("│  Créer un compte          │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrez votre adresse      │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrer \"q\" pour quitter   │");
        System.out.println("╰───────────────────────────╯");     
    }

    public void creerCompteVille() {
        System.out.println("╭───────────────────────────╮");
        System.out.println("│  Créer un compte          │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrez votre ville        │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrer \"q\" pour quitter   │");
        System.out.println("╰───────────────────────────╯");     
    }

    public void creerCompteCodePostal() {
        System.out.println("╭───────────────────────────╮");
        System.out.println("│  Créer un compte          │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrez votre code postal  │");
        System.out.println("├───────────────────────────┤");
        System.out.println("│ Entrer \"q\" pour quitter   │");
        System.out.println("╰───────────────────────────╯");     
    }


    public void menuConnexionEmail() {
        System.out.println("╭────────────────────────────╮");
        System.out.println("│  Connexion                 │");
        System.out.println("├────────────────────────────┤");
        System.out.println("│ Entrez votre adresse email │");
        System.out.println("├────────────────────────────┤");
        System.out.println("│ Entrer \"q\" pour quitter    │");
        System.out.println("╰────────────────────────────╯");     
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
        System.out.println(" |||| 1 - Choisir un autre magasin                                   |                                                                ||||");
        System.out.println(" |||| 2 - Choisir un autre mode de réception                         |                                                                ||||");
        System.out.println(" |||| 3 - Passer une commande                                        |                                                                ||||");
        System.out.println(" |||| 4 - Changer de compte                                          |                                                                ||||");
        System.out.println(" |||| 5 - Quitter l'application                                      |                                                                ||||");
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
        System.out.println(" ||||   1 - En magasin                                               |                                                                ||||");
        System.out.println(" ||||   2 - En ligne                                                 |                                                                ||||");
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

    public void menuCommander() {
        System.out.println("     _______________________________________________________________   _______________________________________________________________  ");
        System.out.println(" .-/|                                                               \\ /                                                               |\\-.");
        System.out.println(" ||||                   COMMANDER UN LIVRE                           |                                                                ||||");
        System.out.println(" ||||________________________________________________________________|                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" |||| 1 - Chercher un livre                                          |                                                                ||||");
        System.out.println(" |||| 2 - Livres recommandés                                         |                                                                ||||");
        System.out.println(" |||| 3 - Meilleurs ventes                                           |                                                                ||||");
        System.out.println(" |||| 4 - Revenir en arrière                                         |                                                                ||||");
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

    public void menuChercherLivre() {
        System.out.println("     _______________________________________________________________   _______________________________________________________________  ");
        System.out.println(" .-/|                                                               \\ /                                                               |\\-.");
        System.out.println(" ||||                   COMMANDER UN LIVRE                           |                                                                ||||");
        System.out.println(" ||||________________________________________________________________|                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" ||||  Veuillez entrer le nom d'un livre                             |                                                                ||||");
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

    public void menuVendeur() {
        System.out.println("     _______________________________________________________________   _______________________________________________________________  ");
        System.out.println(" .-/|                                                               \\ /                                                               |\\-.");
        System.out.println(" ||||                    VENDEUR                                     |                                                                ||||");
        System.out.println(" ||||________________________________________________________________|                                                                ||||");
        System.out.println(" ||||                                                                |                                                                ||||");
        System.out.println(" |||| 1 - Ajouter un livre                                           |                                                                ||||");
        System.out.println(" |||| 2 - Mettre à jour la quantité disponible d’un livre            |                                                                ||||");
        System.out.println(" |||| 3 - Vérifier la disponibilité d’un livre                       |                                                                ||||");
        System.out.println(" |||| 4 - Passer une commande pour un client en magasin              |                                                                ||||");
        System.out.println(" |||| 5 - Transférer un livre d’une autre librairie                  |                                                                ||||");
        System.out.println(" |||| 6 - Changer de compte                                          |                                                                ||||");
        System.out.println(" |||| 7 - Quitter l'application                                      |                                                                ||||");
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
        System.out.println(" |||| 1 - Créer un compte vendeur                                    |                                                                ||||");
        System.out.println(" |||| 2 - Ajouter une nouvelle librairie                             |                                                                ||||");
        System.out.println(" |||| 3 - Gérer les stocks globaux                                   |                                                                ||||");
        System.out.println(" |||| 4 - Consulter les statistiques de vente                        |                                                                ||||");
        System.out.println(" |||| 5 - Changer de compte                                          |                                                                ||||");
        System.out.println(" |||| 6 - Quitter l'application                                      |                                                                ||||");
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

    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static void main(String[] args) {
        AppLibrairie app = new AppLibrairie();
        app.run();
    }

}








                                                                                                                                        