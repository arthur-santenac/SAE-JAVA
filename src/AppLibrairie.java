import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppLibrairie {

    public static boolean quitterAppli = false;
    private ConnexionMySQL connexionMySQL = null;
    private Statement st;

    private MagasinBD magasinBD;
    private ClientBD clientBD;
    private AdminBD adminBD;
    private VendeurBD vendeurBD;
    private CommandeBD commandeBD;

    private Client utilisateur;
    private Commande panier;
    private char enLigne;
    private char modeLivraison;
    private String compte;

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
        this.magasinBD = new MagasinBD(connexionMySQL);
        this.adminBD = new AdminBD(connexionMySQL);
        this.commandeBD = new CommandeBD(connexionMySQL);

        Menu.choisirCreerOuConnecter();
        String connectionOuCreer = System.console().readLine();
        connectionOuCreer = connectionOuCreer.strip();
        if (connectionOuCreer.equals("1")) {

            while (!AppLibrairie.quitterAppli) {
                try {
                    this.compte = Connexion();
                    if (this.compte.equals("client")) {
                        runClient();
                    } else if (this.compte.substring(0, 7).equals("vendeur")) {
                        // a faire
                        runVendeur();
                    } else if (this.compte.equals("administrateur")) {
                        runAdministrateur();
                    } else if (this.compte.equals("quitter")) {
                        AppLibrairie.quitterAppli = true;
                    }
                } catch (MauvaisMotDePasseExeption e) {
                    System.out.println("Mauvais identifiants");
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

    public String Connexion() throws MauvaisMotDePasseExeption {
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

        String rep = this.clientBD.Connexion(email, mdp, this);
        if (rep.equals("mauvaisMdp")) {
            throw new MauvaisMotDePasseExeption();
        }
        return rep;

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

        ps = connexionMySQL.prepareStatement("insert into CONNEXION values (?,?,?,?)");
        ps.setString(1, email);
        ps.setString(2, mdp);
        ps.setInt(3, idcli);
        ps.setString(4, "client");
        ps.executeUpdate();
    }

    public void runClient() {
        try {
            this.enLigne = '1';
            this.magasin = choisirMagasin();
            this.modeLivraison = choisirModeLivraison();
            this.panier = new Commande('1', modeLivraison, magasin, utilisateur);
            while (!AppLibrairie.quitterAppli) {
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
                    verifierStock();
                } else if (option.equals("5")) {
                    run();
                } else if (option.equals("6") || option.equals("quitter") || option.equals("q")
                        || option.equals("quit")) {
                    AppLibrairie.quitterAppli = true;
                } else {
                    erreur();
                }
            }
        } catch (SQLException e) {

        }
    }

    public Magasin choisirMagasin() throws SQLException {
        List<Magasin> listeMagasin = this.magasinBD.listeDesMagasins();
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

    public char choisirModeLivraison() {
        Menu.choisirModeLivraison();
        String modeLivraison = System.console().readLine();
        modeLivraison = modeLivraison.strip();
        if (modeLivraison.equals("1")) {
            return '0';
        } else if (modeLivraison.equals("2")) {
            return '1';
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
                livresRecommande();
            } else if (commander.equals("3")) {
                try {
                    consulterCatalogue();  
                } catch (SQLException e) {
                    erreur();
                }
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

    public void livresRecommande() {
        try {
            boolean quitter = false;
            List<Livre> livresRec = this.clientBD.onVousRecommande(this.utilisateur.getIdCli());
            List<Livre> sousLivres;
            if (livresRec.size() >= 34) {
                sousLivres = livresRec.subList(0, 34);
            } else {
                sousLivres = livresRec;
            }
            while (!quitter) {
                try {
                    Menu.livresRecommande(livresRec);
                    String option = System.console().readLine();
                    option = option.strip();
                    if (option.equals("quitter") || option.equals("q") || option.equals("quit")) {
                        quitter = true;
                    } else {
                        int optionInt = Integer.parseInt(option);
                        if (optionInt > 0 && optionInt < sousLivres.size()) {
                            Menu.qte();;
                            String qte = System.console().readLine();
                            qte = qte.strip();
                            int qteInt = Integer.parseInt(qte);
                            this.panier.ajouterDetailsCommande(sousLivres.get(optionInt - 1), qteInt);
                        } else {
                            erreur();
                        }
                    }
                } catch (NumberFormatException e) {
                    erreur();
                }
            }

        } catch (SQLException e) {
            erreur();
        }
    }

    public void consulterCatalogue() throws SQLException{
        st = connexionMySQL.createStatement();
        ResultSet set = st.executeQuery(
               "select * from LIVRE natural join MAGASIN where nommag = \"" + magasin.getNomMag() + "\"");
        int pageActuel = 1;
        List<Livre> listeLivre = new ArrayList<>();
        while (set.next()) {
			listeLivre.add(new Livre(set.getString("isbn"), set.getString("titre"), set.getInt("nbpages"), set.getInt("datepubli"), set.getDouble("prix")));
		}
        int nbPageMax = (int) (listeLivre.size() / 34 + 1);
        boolean quitter = false;
        while (!quitter) {
            try {
                List<Livre> sousListe;
                try {
                    sousListe = listeLivre.subList(pageActuel * 34 - 34, pageActuel * 34);
                } catch (IndexOutOfBoundsException e) {
                    sousListe = listeLivre.subList(pageActuel * 34 - 34, listeLivre.size());
                }
                Menu.consulterCatalogue(sousListe);
                String option = System.console().readLine();
                option = option.strip();
                if (option.equals("<")) {
                    if (pageActuel > 1) {
                        --pageActuel;
                    }
                } else if (option.equals(">")) {
                    if (pageActuel < nbPageMax) {
                        ++pageActuel;
                    }
                } else if (option.equals("q") || option.equals("quitter") || option.equals("quit")) {
                    quitter = true;
                } else {
                    int optionInt = Integer.parseInt(option);
                    if (optionInt > 0 && optionInt <= 34) {
                        Menu.qte();;
                        String qte = System.console().readLine();
                        qte = qte.strip();
                        int qteInt = Integer.parseInt(qte);
                        this.panier.ajouterDetailsCommande(sousListe.get(optionInt - 1), qteInt);
                    } else {
                        erreur();
                    }

                }
            } catch (NumberFormatException e) {
                erreur();
            }

        }
        
    } 

    public void consulterPanier() {
        if (Menu.consulterPanier(panier)) {
            String option = System.console().readLine();
            option = option.strip();
            if (option.equals("1")) {
                finaliserCommande();
            } else if (option.equals("2")) {
                supprPanier();
            } else if (option.equals("3") || option.equals("q") || option.equals("quitter") || option.equals("quit")) {
            } else {
                erreur();
                consulterPanier();
            }
        } else {
            System.console().readLine();
        }
    }

    public void finaliserCommande() {
        try {
            int maxNumCom = this.commandeBD.maxNumCom() + 1;
            this.commandeBD.insererCommande(maxNumCom, enLigne, modeLivraison, this.utilisateur.getIdCli(),
                    this.magasin.getIdMag());
            for (int i = 0; i < this.panier.size(); i++) {
                this.commandeBD.insererDetailCommande(maxNumCom, i + 1,
                        this.panier.getDetailsCommande().get(i).getQte(),
                        this.panier.getDetailsCommande().get(i).getPrixVente(),
                        this.panier.getDetailsCommande().get(i).getLivre().getIsbn());
            }
            this.panier = new Commande('1', modeLivraison, magasin, utilisateur);
        } catch (SQLException e) {
            System.out.println("erreur sql");
            System.console().readLine();
        }
    }

    public void supprPanier() {
        Menu.supprPanier(this.panier);
        String option = System.console().readLine();
        option = option.strip();
        if (option.equals("1")) {
            this.panier = new Commande(enLigne, modeLivraison, magasin, utilisateur);
        } else if (option.equals("2")) {
            try {
                Menu.supprPanierUneCom(this.panier);
                option = System.console().readLine();
                option = option.strip();
                if (!(option.equals("q") || option.equals("quitter") || option.equals("quit"))) {
                    if (Integer.parseInt(option) > 0 && Integer.parseInt(option) <= this.panier.size()) {
                        this.panier.getDetailsCommande().remove(Integer.parseInt(option) - 1);
                    } else {
                        erreur();
                    }
                    supprPanier();
                }
            } catch (NumberFormatException e) {
                erreur();
                supprPanier();
            }
        } else if (option.equals("3") || option.equals("q") || option.equals("quitter") || option.equals("quit")) {
        } else {
            erreur();
            supprPanier();
        }
    }

    public void verifierStock() throws SQLException {
        Menu.chercherLivre();
        String chercher = System.console().readLine();
        chercher = chercher.strip();
        if (chercher.equals("q") || chercher.equals("quit") || chercher.equals("quitter")) {
            return;
        }
        boolean trouve = false;
        List<Livre> listeLivre = new ArrayList<>();
        st = connexionMySQL.createStatement();
        ResultSet set = st.executeQuery(
                "select * from LIVRE natural join MAGASIN where nommag = \"" + magasin.getNomMag() + "\"");
        while (set.next()) {
            if (set.getString(2).equals(chercher)) {
                listeLivre.add(
                        new Livre(set.getString(1), set.getString(2), set.getInt(3), set.getInt(4), set.getDouble(5)));
                trouve = true;
            }
        }
        if (!trouve) {
            System.out.println("le livre n'existe pas");
            erreur();
            System.console().readLine();
            chercherLivre();
        } else {
            Menu.livreEnMagasin();
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
                "select * from LIVRE");
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
                panier.ajouterDetailsCommande(listeLivre.get(numLivre - 1), numQte);
            } else {
                erreur();
                proposerChercherLivre(listeLivre);
            }
        } catch (NumberFormatException e) {
            erreur();
            proposerChercherLivre(listeLivre);
        }
    }

    public void runAdministrateur() {
        Menu.admin();
        while (!AppLibrairie.quitterAppli) {
            String option = System.console().readLine();
            option = option.strip().toLowerCase();
            if (option.equals("1")) {
                creerVendeur();
            } else if (option.equals("2")) {
                suppVendeur();
            } else if (option.equals("3")) {
                listeVendeurs();
            } else if (option.equals("4")) {
                creerLib();
            } else if (option.equals("5")) {
                suppLib();
            } else if (option.equals("6")) {
                listeLibs();
            } else if (option.equals("7")) {

            } else if (option.equals("8")) {

            } else if (option.equals("9")) {

            } else if (option.equals("10")) {
                AppLibrairie.quitterAppli = true;
            } else {
                this.erreur();
            }
        }
    }

    

    public void creerVendeur() {
        Integer idV = null;
        String nomV = null;
        String prenomV = null;
        String adresseV = null;
        String codePostalV = null;
        String villeV = null;
        String emailV = null;
        String mdp = null;
        try {
            idV = this.clientBD.maxNum() + 1;
        } catch (SQLException e) {
            System.out.println("Il y a une erreur avec l'id du vendeur");
            runAdministrateur();
        }
        nomV = nomVendeur();
        prenomV = prenomVendeur(nomV);
        adresseV = adresseVendeur(nomV, prenomV);
        codePostalV = codePostalVendeur(nomV, prenomV, adresseV);
        villeV = villeVendeur(nomV, prenomV, adresseV, codePostalV);
        emailV = emailVendeur(nomV, prenomV, adresseV, codePostalV, villeV);
        mdp = mdpVendeur(nomV, prenomV, adresseV, codePostalV, villeV, emailV);
        Client vendeur = new Client(nomV, prenomV, adresseV, codePostalV, villeV, idV);
        try{
            this.clientBD.insererVendeur(vendeur,emailV, mdp);
        }catch(SQLException e){
            System.out.println("Il y a une erreur avec l'insertion du vendeur");
            runAdministrateur();
        }
        runAdministrateur();
        
    }

    private String nomVendeur(){
        Menu.adminNomVendeur();
        String option = System.console().readLine();
        option = option.strip();
        String nomVendeur = null;
        if(option.equals("q") || option.equals("quitter") || option.equals("Quitter")){
            runAdministrateur();
        }
        else{
            nomVendeur = option;
        }
        return nomVendeur;
    }

    private String prenomVendeur(String nom){
        Menu.adminPrenomVendeur(nom);
        String option = System.console().readLine();
        option = option.strip();
        if(option.equals("q") || option.equals("quitter") || option.equals("Quitter")){
            runAdministrateur();
        }
        String prenomVendeur = option;
        return prenomVendeur;
    }

    private String adresseVendeur(String nom, String prenom){
        Menu.adminAdresseVendeur(nom,prenom);
        String option = System.console().readLine();
        option = option.strip();
        if(option.equals("q") || option.equals("quitter") || option.equals("Quitter")){
            runAdministrateur();
        }
        String adresseVendeur = option;
        return adresseVendeur;
    }

    private String codePostalVendeur(String nom, String prenom, String adresse){
        Menu.adminCodePostalVendeur(nom, prenom, adresse);
        String option = System.console().readLine();
        option = option.strip();
        if(option.equals("q") || option.equals("quitter") || option.equals("Quitter")){
            runAdministrateur();
        }
        String codePostalV = option;
        return codePostalV;
    }

    private String villeVendeur(String nom, String prenom, String adresse, String codePostal){
        Menu.adminVilleVendeur(nom, prenom, adresse, codePostal);
        String option = System.console().readLine();
        option = option.strip();
        if(option.equals("q") || option.equals("quitter") || option.equals("Quitter")){
            runAdministrateur();
        }
        String villeVendeur = option;
        return villeVendeur;
    }

    private String emailVendeur(String nom, String prenom, String adresse, String codePostal, String ville){
        Menu.adminEmailVendeur(nom, prenom, adresse, codePostal, ville);
        String option = System.console().readLine();
        option = option.strip();
        if(option.equals("q") || option.equals("quitter") || option.equals("Quitter")){
            runAdministrateur();
        }
        String emailVendeur = option;
        return emailVendeur;
    }

    private String mdpVendeur(String nom, String prenom, String adresse, String codePostal, String ville, String email){
        Menu.adminMdpVendeur(nom, prenom, adresse, codePostal, ville, email);
        String option = System.console().readLine();
        option = option.strip();
        if(option.equals("q") || option.equals("quitter") || option.equals("Quitter")){
            runAdministrateur();
        }
        String mdpVendeur = option;
        return mdpVendeur;
    }

    public void suppVendeur() {
        Client vendeur = null;
        Integer idCli = null;
        try {
            List<Client> listeVendeurs = this.clientBD.listeDesVendeurs();
            Menu.adminSupVendeur(listeVendeurs);
        } catch (SQLException ex) {
            System.out.println("Erreur SQL !");
        }
        String id = System.console().readLine();
        id = id.strip();
        if (id.equals("q") || id.equals("quitter") || id.equals("quit")) {
            Menu.admin();
        } else {
            try {
                idCli = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                System.out.println("Entrez un identifiant valide.");
            }
            try {
                vendeur = this.clientBD.rechercherVendeurParId(idCli);
            } catch (SQLException e) {
                System.out.println("Il n'y a aucune librairie avec cet identifiant");
                suppLib();
            }
            confSuppVendeur(vendeur);

        }

    }

    public void confSuppVendeur(Client client){
        Menu.adminConfirmationSupVendeur(client);
        String confirmation = System.console().readLine();
        confirmation = confirmation.strip().toLowerCase();
        if (confirmation.equals("oui") || confirmation.equals("o")) {
            try {
                int id = client.getIdCli();
                this.clientBD.supprimerVendeur(id);
            } catch (SQLException e) {
                System.out.println("Problèmes rencontrés lors de la suppression de la librairie");
            }
            runAdministrateur();
        } else if (confirmation.equals("non") || confirmation.equals("n")) {
            runAdministrateur();
        } else {
            erreur();
        }
    }


    public void listeVendeurs() {
        try {
            List<Client> listeMagasin = this.clientBD.listeDesVendeurs();
            Menu.adminListeVendeur(listeMagasin);
        } catch (SQLException ex) {
            System.out.println("Erreur d'affichage de la liste des vendeurs !");
        }
        String option = System.console().readLine();
        option = option.strip().toLowerCase();
        if (option.equals("q") || option.equals("quitter")) {
            runAdministrateur();
        } else {
            erreur();
        }
    }

    public void creerLib() {
        Magasin newLibrairie = null;
        String nomLib = nomLib();
        String ville = villeLib(nomLib);
        try {
            int idmag = this.adminBD.maxNumMagasin();
            newLibrairie = new Magasin(idmag, nomLib, ville);
            this.adminBD.insererLibrairie(newLibrairie);
            this.runAdministrateur();
        } catch (SQLException e) {
            System.out.println("Problèmes rencontrés dans l'ajout d'une nouvelle librairie");
            Menu.adminNomLib();
        }

    }

    private String nomLib() {
        Menu.adminNomLib();
        String nom = System.console().readLine();
        nom = nom.strip();
        String nomLib = null;
        if (nom.equals("q") || nom.equals("quitter") || nom.equals("Quitter")) {
            runAdministrateur();
        }
        nomLib = nom;
        return nomLib;
    }

    private String villeLib(String nom) {
        Menu.adminVilleLib(nom);
        String ville = System.console().readLine();
        ville = ville.strip();
        String villeLib = ville;
        return villeLib;
    }

    public void suppLib() {
        Magasin lib = null;
        Integer idMag = null;
        try {
            List<Magasin> listeMagasin = magasinBD.listeDesMagasins();
            Menu.adminSupLib(listeMagasin);
        } catch (SQLException ex) {
            System.out.println("Erreur SQL !");
        }
        String id = System.console().readLine();
        id = id.strip();
        if (id.equals("q") || id.equals("quitter") || id.equals("quit")) {
            Menu.admin();
        } else {
            try {
                idMag = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                System.out.println("Entrez un identifiant valide.");
            }
            try {
                lib = this.magasinBD.rechercherMagasinParId(idMag);
            } catch (SQLException e) {
                System.out.println("Il n'y a aucune librairie avec cet identifiant");
                suppLib();
            }
            confSuppLib(lib);

        }

    }


    public void confSuppLib(Magasin mag){
        Menu.adminConfirmationSup(mag);

        String confirmation = System.console().readLine();
        confirmation = confirmation.strip().toLowerCase();
        if (confirmation.equals("oui") || confirmation.equals("o")) {
            try {
                int id = mag.getIdMag();
                this.adminBD.supprimerLibrairie(id);
            } catch (SQLException e) {
                System.out.println("Problèmes rencontrés lors de la suppression de la librairie");
            }
            runAdministrateur();
        } else if (confirmation.equals("non") || confirmation.equals("n")) {
            runAdministrateur();
        } else {
            erreur();
        }
    }

    public void listeLibs() {
        try {
            List<Magasin> listeMagasin = magasinBD.listeDesMagasins();
            Menu.adminListeLib(listeMagasin);
        } catch (SQLException ex) {
            System.out.println("Erreur SQL !");
        }
        String option = System.console().readLine();
        option = option.strip().toLowerCase();
        if (option.equals("q") || option.equals("quitter")) {
            runAdministrateur();
        } else {
            erreur();
        }
    }

    public void adminStats(){
        Menu.adminStats();
        String option = System.console().readLine();
            option = option.strip().toLowerCase();
            if (option.equals("1")) {
                
            } else if (option.equals("2")) {
                
            } else if (option.equals("3")) {
                
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
        if (aAjouter <= 0) {
            String res = string.substring(0, longeur - 1) + " ";
            return res;
        }
        String padding = "";
        for (int i = 0; i < aAjouter; i++) {
            padding = padding + " ";
        }
        return string + padding;
    }

    public void runVendeur() {
        this.vendeurBD = new VendeurBD(connexionMySQL);
        try {
            this.panier = new Commande('1', modeLivraison, magasin, utilisateur);
            while (!AppLibrairie.quitterAppli) {
                Menu.vendeur();
                String option = System.console().readLine();
                option = option.strip();
                if (option.equals("1")) {
                    ajouteLivre();
                } else if (option.equals("2")) {
                    majQte();
                } else if (option.equals("3")) {
                    dispo();
                } else if (option.equals("4")) {
                    this.enLigne = 0;
                    commanderVendeur();
                } else if (option.equals("5")) {
                    transfert();
                } else if (option.equals("6")) {
                    run();
                } else if (option.equals("7") || option.equals("quitter") || option.equals("q")
                        || option.equals("quit")) {
                    AppLibrairie.quitterAppli = true;
                } else {
                    erreur();
                }
            }
        } catch (SQLException e) {
            System.out.println("erreur");
        }

    }

    public void setUtilisateur(Client utilisateur) {
        this.utilisateur = utilisateur;
    }

    public boolean ajouteLivre() throws SQLException {
        char idMag = this.compte.charAt(this.compte.length() - 1);
        int idMagInt = Character.getNumericValue(idMag);
        Menu.vendeurRecupIdLivreAjout();
        String idlivre = System.console().readLine();
        Menu.vendeurRecupQte();
        String quantite = System.console().readLine();
        int quantiteInt = Integer.parseInt(quantite);
        return (this.vendeurBD.ajouteLivre(idMagInt, idlivre, quantiteInt));
    }

    public boolean majQte() throws SQLException {
        char idMag = this.compte.charAt(this.compte.length() - 1);
        int idMagInt = Character.getNumericValue(idMag);
        Menu.vendeurRecupIdLivreModif();
        String idlivre = System.console().readLine();
        Menu.vendeurRecupQte();
        String quantite = System.console().readLine();
        int quantiteInt = Integer.parseInt(quantite);
        return (this.vendeurBD.majQte(idMagInt, idlivre, quantiteInt, true));
    }

    public boolean dispo() throws SQLException {
        char idMag = this.compte.charAt(this.compte.length() - 1);
        int idMagInt = Character.getNumericValue(idMag);
        Menu.vendeurRecupIdLivreVerif();
        String idlivre = System.console().readLine();
        Menu.vendeurRecupQte();
        String quantite = System.console().readLine();
        int quantiteInt = Integer.parseInt(quantite);
        return (this.vendeurBD.dispo(idMagInt, idlivre, quantiteInt, true));
    }

    public Magasin choisirMagasinTransfert(String idlivre, int quantiteInt) throws SQLException {
        List<Magasin> listeMagasin = new ArrayList<>();
        st = connexionMySQL.createStatement();
        ResultSet set = st.executeQuery("select * from POSSEDER natural join MAGASIN where isbn = " + idlivre
                + " AND qte >= " + quantiteInt + ";");
        while (set.next()) {
            listeMagasin.add(new Magasin(set.getInt(1), set.getString(2), set.getString(3)));
        }
        Menu.choisirMagasinTransfet(listeMagasin);
        String magasin = System.console().readLine();
        magasin = magasin.strip();
        try {
            int numMagasin = Integer.parseInt(magasin);
            if (numMagasin >= 1 && numMagasin <= magasinBD.listeDesMagasins().size()) {
                return magasinBD.listeDesMagasins().get(numMagasin - 1);
            } else {
                erreur();
                return choisirMagasinTransfert(idlivre, quantiteInt);
            }
        } catch (NumberFormatException e) {
            erreur();
            return choisirMagasinTransfert(idlivre, quantiteInt);
        }
    }

    public boolean transfert() throws SQLException {
        char idMag = this.compte.charAt(this.compte.length() - 1);
        int idMagInt = Character.getNumericValue(idMag);
        Menu.vendeurRecupIdLivreVerif();
        String idlivre = System.console().readLine();
        Menu.vendeurRecupQte();
        String quantite = System.console().readLine();
        int quantiteInt = Integer.parseInt(quantite);
        Magasin MagPossede = choisirMagasinTransfert(idlivre, quantiteInt);
        return (this.vendeurBD.transfer(MagPossede.getIdMag(), idMagInt, idlivre, quantiteInt));
    }

    public void commanderVendeur() {
        this.modeLivraison = choisirModeLivraison();
        boolean quitter = false;
        while (!quitter) {
            Menu.commanderVendeur();
            String commander = System.console().readLine();
            commander = commander.strip();
            if (commander.equals("1")) {
                try {
                    chercherLivreVendeur();
                } catch (SQLException e) {
                    System.out.println("erreur en sql");
                    System.console().readLine();
                }
            } else if (commander.equals("2")) {
                consulterPanierVendeur();
            } else if (commander.equals("3") || commander.equals("quitter") || commander.equals("q")
                    || commander.equals("quit")) {
                quitter = true;
            } else {

                erreur();
            }
        }
    }

    public void chercherLivreVendeur() throws SQLException {
        Menu.chercherLivre();
        String chercher = System.console().readLine();
        chercher = chercher.strip();
        if (chercher.equals("q") || chercher.equals("quit") || chercher.equals("quitter")) {
            return;
        }
        st = connexionMySQL.createStatement();
        ResultSet set = st.executeQuery(
                "select * from LIVRE where isbn =" + chercher + ";");
        Livre livre = new Livre(chercher, chercher, enLigne, modeLivraison, enLigne);
        if (set.next()) {
            livre = new Livre(set.getString(1), set.getString(2), set.getInt(3), set.getInt(4), set.getDouble(5));
        }

        char idMag = this.compte.charAt(this.compte.length() - 1);
        int idMagInt = Character.getNumericValue(idMag);
        Menu.qte();
        String qte = System.console().readLine();
        qte = qte.strip();

        if (qte.equals("q") || qte.equals("quitter") || qte.equals("quit")) {
            return;
        }
        try {
            if (this.vendeurBD.dispo(idMagInt, chercher, Integer.parseInt(qte), true)) {

                int numQte = Integer.parseInt(qte);
                panier.ajouterDetailsCommande(livre, numQte);
            } else {
                Menu.demandeTransfert();
                String rep = System.console().readLine();
                if (rep.equals("oui")) {
                    transfert();
                } else {
                    chercherLivreVendeur();
                }

            }
        } catch (NumberFormatException e) {

        }
    }

    public static void main(String[] args) {
        AppLibrairie app = new AppLibrairie();
        app.run();
    }

    public void finaliserCommandeVendeur() {
        try {
            char idMag = this.compte.charAt(this.compte.length() - 1);
            int idMagInt = Character.getNumericValue(idMag);
            int maxNumCom = this.commandeBD.maxNumCom() + 1;
            this.commandeBD.insererCommande(maxNumCom, enLigne, modeLivraison, this.utilisateur.getIdCli(),
                    idMagInt);
            for (int i = 0; i < this.panier.size(); i++) {
                this.commandeBD.insererDetailCommande(maxNumCom, i + 1,
                        this.panier.getDetailsCommande().get(i).getQte(),
                        this.panier.getDetailsCommande().get(i).getPrixVente(),
                        this.panier.getDetailsCommande().get(i).getLivre().getIsbn());
            }
            this.panier = new Commande('1', modeLivraison, magasin, utilisateur);
        } catch (SQLException e) {
            System.out.println("erreur sql");
            System.console().readLine();
        }
    }

    public void consulterPanierVendeur() {
        if (Menu.consulterPanier(panier)) {
            String option = System.console().readLine();
            option = option.strip();
            if (option.equals("1")) {
                finaliserCommandeVendeur();
            } else if (option.equals("2")) {
                supprPanier();
            } else if (option.equals("3") || option.equals("q") || option.equals("quitter") || option.equals("quit")) {
            } else {
                erreur();
                consulterPanier();
            }
        } else {
            System.console().readLine();
        }
    }

}
