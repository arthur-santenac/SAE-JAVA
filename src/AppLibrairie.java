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
    private ClientBD clientBD;

    private Client utilisateur;
    private Commande panier;
    private String enLigne;
    private Magasin magasin;

    public AppLibrairie() {

    }

    public void run() {

        if (connexionMySQL == null) {
            try {
                if (!connexionBD()) {
                    return;
                }

            } catch (SQLException e) {
                System.out.println("La connexion a échouée");
                System.console().readLine();
                run();
            } catch (ClassNotFoundException e) {
                System.out.println("La classe n'a pas été trouvé");
                System.console().readLine();
                return;
            }
        }

        this.clientBD = new ClientBD(connexionMySQL);

        Menu.choisirCreerOuConnecter();
        String connectionOuCreer = System.console().readLine();
        connectionOuCreer = connectionOuCreer.strip();
        if (connectionOuCreer.equals("1")) {

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
                } catch (MauvaisMotDePasseExeption e) {
                    System.out.println("Mauvais identifiants");
                    erreur();
                    run();
                } catch (SQLException e) {
                    System.out.println("La connexion a échouée");
                    erreur();
                    run();
                }
            }
        } else if (connectionOuCreer.equals("2")) {
            try {
                creerUnCompte();
                run();
            } catch (SQLException e) {
                System.out.println("La création a échouée");
            }
        } else if (connectionOuCreer.equals("quitter") || connectionOuCreer.equals("q")
                || connectionOuCreer.equals("quit")) {
        } else {
            erreur();
            run();
        }

    }

    public String Connexion() throws MauvaisMotDePasseExeption, SQLException {

        Menu.connexionEmail();
        String email = System.console().readLine();
        email = email.strip();

        if (email.equals("quitter") || email.equals("q") || email.equals("quit")) {
            return "quitter";
        }

        Menu.connexionMdp();
        String mdp = System.console().readLine();
        mdp = mdp.strip();

        if (mdp.equals("quitter") || mdp.equals("q") || mdp.equals("quit")) {
            return "quitter";
        }

        st = connexionMySQL.createStatement();
        ResultSet set = st.executeQuery("select * from CONNEXION natural join CLIENT");

        while (set.next()) {
            if (email.equals(set.getString(2)) && mdp.equals(set.getString(3))) {
                this.utilisateur = new Client(set.getString(5), set.getString(6), set.getString(7), set.getInt(8),
                        set.getString(9), set.getInt(1));
                return set.getString(4);
            }
        }

        throw new MauvaisMotDePasseExeption();
    }

    public void creerUnCompte() throws SQLException {

        Menu.creerCompteEmail();
        String email = System.console().readLine();
        email = email.strip();
        if (email.equals("quitter") || email.equals("q") || email.equals("quit")) {
            return;
        }

        Menu.creerCompteMdp();
        String mdp = System.console().readLine();
        mdp = mdp.strip();
        if (mdp.equals("quitter") || mdp.equals("q") || mdp.equals("quit")) {
            return;
        }

        Menu.creerCompteNom();
        String nom = System.console().readLine();
        nom = nom.strip();
        if (nom.equals("quitter") || nom.equals("q") || nom.equals("quit")) {
            return;
        }

        Menu.creerComptePrenom();
        String prenom = System.console().readLine();
        prenom = prenom.strip();
        if (prenom.equals("quitter") || prenom.equals("q") || prenom.equals("quit")) {
            return;
        }

        Menu.creerCompteAdresse();
        String adresse = System.console().readLine();
        adresse = adresse.strip();
        if (adresse.equals("quitter") || adresse.equals("q") || adresse.equals("quit")) {
            return;
        }

        Menu.creerCompteVille();
        String ville = System.console().readLine();
        ville = ville.strip();
        if (ville.equals("quitter") || ville.equals("q") || ville.equals("quit")) {
            return;
        }

        Menu.creerCompteCodePostal();
        String codePostal = System.console().readLine();
        codePostal = codePostal.strip();
        if (codePostal.equals("quitter") || codePostal.equals("q") || codePostal.equals("quit")) {
            return;
        }
        int codePostalInt = Integer.parseInt(codePostal);

        int idcli = this.clientBD.maxNum();

        PreparedStatement ps = connexionMySQL.prepareStatement("insert into CLIENT values (?,?,?,?,?,?)");
        ps.setInt(1, idcli);
        ps.setString(2, nom);
        ps.setString(3, prenom);
        ps.setString(4, adresse);
        ps.setInt(5, codePostalInt);
        ps.setString(6, ville);
        ps.executeUpdate();

        ps = connexionMySQL.prepareStatement("insert into CONNEXION values (?,?,?, ?)");
        ps.setString(1, email);
        ps.setString(2, mdp);
        ps.setInt(3, idcli);
        ps.setString(4, "client");
        ps.executeUpdate();
    }

    public void runClient() {
        try {
            this.magasin = choisirMagasin();
            this.enLigne = choisirModeLivraison();
            this.panier = new Commande(0, null, enLigne, null, magasin, utilisateur);
            while (!AppLibrairie.continuer) {

                Menu.client();
                String option = System.console().readLine();
                option = option.strip();
                if (option.equals("1")) {
                    choisirMagasin();
                } else if (option.equals("2")) {
                    choisirModeLivraison();
                } else if (option.equals("3")) {
                    commander();
                } else if (option.equals("4")) {
                    run();
                } else if (option.equals("5") || option.equals("quitter") || option.equals("q")
                        || option.equals("quit")) {
                    AppLibrairie.continuer = true;
                } else {
                    erreur();
                }
            }
        } catch (SQLException e) {

        }
    }

    public Magasin choisirMagasin() throws SQLException {

        List<Magasin> listeMagasin = new ArrayList<>();
        st = connexionMySQL.createStatement();
        ResultSet set = st.executeQuery("select * from MAGASIN");
        while (set.next()) {
            listeMagasin.add(new Magasin(set.getInt(1), set.getString(2), set.getString(3)));
        }

        Menu.choisirMagasin(listeMagasin);
        String magasin = System.console().readLine();
        magasin = magasin.strip();
        try {
            int numMagasin = Integer.parseInt(magasin);
            if (numMagasin >= 1 && numMagasin <= listeMagasin.size()) {
                return listeMagasin.get(numMagasin - 1);
            } else {
                erreur();
                return choisirMagasin();
            }
        } catch (NumberFormatException e) {
            erreur();
            return choisirMagasin();
        }
    }

    public String choisirModeLivraison() {

        Menu.choisirModeLivraison();
        String modeLivraison = System.console().readLine();
        modeLivraison = modeLivraison.strip();
        if (modeLivraison.equals("1")) {
            return "0";
        } else if (modeLivraison.equals("2")) {
            return "1";
        } else {
            erreur();
            return choisirModeLivraison();
        }
    }

    public void commander() {
        boolean quitter = false;
        while (!quitter) {

            Menu.commander();
            String commander = System.console().readLine();
            commander = commander.strip();
            if (commander.equals("1")) {
                try {
                    chercherLivre();
                } catch (SQLException e) {
                    System.out.println("erreur en sql");
                    System.console().readLine();
                }
            } else if (commander.equals("2")) {

            } else if (commander.equals("3")) {
                meilleursVentes();
            } else if (commander.equals("4")) {
                consulterPanier();
            } else if (commander.equals("5") || commander.equals("quitter") || commander.equals("q")
                    || commander.equals("quit")) {
                quitter = true;
            } else {
                erreur();
            }
        }
    }

    public void consulterPanier() {

        if (Menu.consulterPanier(panier)) {
            System.console().readLine();
        } else {
            System.console().readLine();
        }
    }

    public void chercherLivre() throws SQLException {

        Menu.chercherLivre();
        String chercher = System.console().readLine();
        chercher = chercher.strip();
        if (chercher.equals("q") || chercher.equals("quit") || chercher.equals("quitter")) {
            return;
        }
        boolean trouver = false;
        List<Livre> listeLivre = new ArrayList<>();
        st = connexionMySQL.createStatement();
        ResultSet set = st.executeQuery(
                "select * from LIVRE natural join MAGASIN where nommag = \"" + magasin.getNomMag() + "\"");
        while (set.next()) {
            if (set.getString(2).equals(chercher)) {
                listeLivre.add(
                        new Livre(set.getString(1), set.getString(2), set.getInt(3), set.getInt(4), set.getDouble(5)));
                trouver = true;
            }
        }
        if (!trouver) {
            System.out.println("le livre n'existe pas");
            erreur();
            System.console().readLine();
            chercherLivre();
        } else {
            proposerChercherLivre(listeLivre);
        }

    }

    public void proposerChercherLivre(List<Livre> listeLivre) {
        try {

            Menu.proposerChercherLivre(listeLivre);
            String quelleLivre = System.console().readLine();
            quelleLivre = quelleLivre.strip();
            if (quelleLivre.equals("q") || quelleLivre.equals("quitter") || quelleLivre.equals("quit")) {
                return;
            }
            int numLivre = Integer.parseInt(quelleLivre);
            if (numLivre >= 1 && numLivre <= listeLivre.size()) {

                Menu.qte();
                String qte = System.console().readLine();
                qte = qte.strip();
                if (qte.equals("q") || qte.equals("quitter") || qte.equals("quit")) {
                    return;
                }
                int numQte = Integer.parseInt(qte);
                panier.ajouterDetailsCommande(panier.size(), listeLivre.get(numLivre - 1), numQte);
                ;
            } else {
                erreur();
                proposerChercherLivre(listeLivre);
            }
        } catch (NumberFormatException e) {
            erreur();
            proposerChercherLivre(listeLivre);
        }
    }

    public void meilleursVentes() {

    }

    public void runVendeur() {
        while (!AppLibrairie.continuer) {
            Menu.vendeur();
            String identifiant = System.console().readLine();
            identifiant = identifiant.strip();
            if (identifiant.equals("q") || identifiant.equals("quitter") || identifiant.equals("quit")) {
                return;
            }
        }
    }

    public void runAdministrateur() {
        while (!AppLibrairie.continuer) {
            Menu.admin();
            String identifiant = System.console().readLine();
            identifiant = identifiant.strip();
            if (identifiant.equals("q") || identifiant.equals("quitter") || identifiant.equals("quit")) {
                return;
            }
        }
    }

    public void erreur() {
        System.out.println("\n" + "Erreur veillez réessayer");
        System.console().readLine();
    }

    public boolean connexionBD() throws SQLException, ClassNotFoundException {
        ConnexionMySQL connexionMySQL = new ConnexionMySQL();
        Menu.connexionIdentBD();
        String identifiant = System.console().readLine();
        if (identifiant.equals("quitter") || identifiant.equals("q") || identifiant.equals("quit")) {
            return false;
        }

        Menu.connexionMdpBD();
        String mdp = System.console().readLine();
        if (mdp.equals("quitter") || mdp.equals("q") || mdp.equals("quit")) {
            return false;
        }

        String serveur = "servinfo-maria";
        String nomBase = "DB" + identifiant;
        connexionMySQL.connecter(identifiant, mdp, serveur, nomBase);
        if (!connexionMySQL.isConnecte()) {
            throw new SQLException();
        }
        this.connexionMySQL = connexionMySQL;
        return true;

    }

    public static String ljust(String string, int longeur) {
        int aAjouter = longeur - string.length();
        if (aAjouter <= 0)
            return string;
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
