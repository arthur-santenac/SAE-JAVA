import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LivreExpress extends Application {

    private ClientBD clientBD;
    private AdminBD adminBD;
    private VendeurBD vendeurBD;
    private MagasinBD magasinBD;
    private CommandeBD commandeBD;

    private Commande panier;

    // ============CONNEXION / CREER COMPTE===============

    private Button boutonConnexion;
    private Button boutonDeconnexion;
    private Button boutonCreerUnCompte;
    private Button boutonCreerLeCompte;
    private TextField identifiant;
    private PasswordField mdp;
    private TextField mdpText;
    private TextField nom;
    private TextField prenom;
    private TextField adresse;
    private TextField ville;
    private TextField codePostal;

    private Label textErreurConnexion;
    private Label textErreurCreerCompte;

    private Scene scene;
    private Stage stage;
    private Client utilisateur;
    private String compte;
    private ConnexionMySQL laConnexion = null;

    // ============CLIENT===============

    private Button ajouteRecherche;
    private Button ajouteCatalogue;
    private Button ajouteRecom;
    private Button recherche;
    private Button boutonPanier;
    private Button boutonRetourClient;

    private ListView<Livre> listeRecherche;
    private ListView<Livre> catalogue;
    private ListView<Livre> recommande;
    private ComboBox<String> filtreTheme;
    private ComboBox<String> filtreFiltre;
    private ComboBox<Integer> qteCatalogue;
    private ComboBox<Integer> qteClient;
    private ComboBox<Integer> qteRecom;
    private ComboBox<Magasin> CBMagasins;

    private TextField txtRecherche;

    private Button btnCommander;
    private Button btnSupprSel;
    private Button btnViderPanier;

    private ListView<DetailCommande> listeArticles;

    private RadioButton livraisonDomicile;
    private RadioButton retraitMagasin;
    private ToggleGroup modeLivraison;

    // ============VENDEUR===============

    private Button btnAjout;
    private Button btnStock;
    private Button btnTransfert;
    private int idMag;
    private ListView<String> listeRechercheVendeur;
    private Button rechercheV;

    private TextField idAjout;
    private Button ajouter;
    private Button finaliser;
    private Button supprimer;
    private ListView<String> resumeCommande;

    // ============ADMIN===============

    private Button btnRetourAdmin;
    private Button btnLib;
    private Button btnAddLib;
    private Button btnSuppLib;

    private Button btnVend;
    private Button btnAddVendeur;
    private Button btnSuppVendeur;

    private Button btnStats;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(LivreExpress.class, args);
    }

    @Override
    public void init() {

        this.panier = new Commande('0', '0', null, utilisateur);

        this.identifiant = new TextField();
        this.mdp = new PasswordField();
        this.mdpText = new TextField();
        this.adresse = new TextField();
        this.nom = new TextField();
        this.prenom = new TextField();
        this.adresse = new TextField();
        this.ville = new TextField();
        this.codePostal = new TextField();
        this.textErreurConnexion = new Label();
        this.textErreurCreerCompte = new Label();
        this.boutonConnexion = new Button("Connexion");
        this.boutonDeconnexion = new Button("Déconnexion");
        this.boutonCreerUnCompte = new Button("Créer un compte");
        this.boutonCreerLeCompte = new Button("Créer le compte");

        ControleurConnexion controleurConnexion = new ControleurConnexion(this, this.identifiant, this.mdp);
        ControleurPageCreerCompte controleurPageCreerCompte = new ControleurPageCreerCompte(this);
        ControleurCreerCompte controleurCreerCompte = new ControleurCreerCompte(this, this.identifiant, this.mdpText,
                this.nom, this.prenom, this.adresse, this.ville, this.codePostal);

        this.boutonConnexion.setOnAction(controleurConnexion);
        this.boutonDeconnexion.setOnAction(controleurConnexion);
        this.boutonCreerUnCompte.setOnAction(controleurPageCreerCompte);
        this.boutonCreerLeCompte.setOnAction(controleurCreerCompte);

        // ============CLIENT===============

        

        this.qteClient = new ComboBox<>();
        this.qteClient.getItems().addAll(1, 2, 3, 4, 5);
        this.qteClient.setValue(1);
        this.qteCatalogue = new ComboBox<>();
        this.qteCatalogue.getItems().addAll(1, 2, 3, 4, 5);
        this.qteCatalogue.setValue(1);
        this.qteRecom = new ComboBox<>();
        this.qteRecom.getItems().addAll(1, 2, 3, 4, 5);
        this.qteRecom.setValue(1);

        this.recherche = new Button("Rechercher");
        this.recherche.setOnAction(e -> affichePageClient());

        this.txtRecherche = new TextField();
        this.txtRecherche.setPromptText("Entrez le nom d'un livre");
        this.txtRecherche.setMaxWidth(Double.MAX_VALUE);

        this.listeRecherche = new ListView<>();
        this.catalogue = new ListView<>();
        this.recommande = new ListView<>();


        this.filtreTheme = new ComboBox<>();
        this.filtreTheme.getItems().addAll("Tout", "Arts et Loisirs", "Histoire et Géographie",
                "Informatique, généralités", "Langues", "Littérature", "Philosophie et psychologie", "Religion",
                "Science naturelles et mathématiques", "Sciences sociales", "Technologie et sciences appliqués");
        this.filtreTheme.setValue("Tout");
        this.filtreTheme.setOnAction(e -> affichePageClient());

        this.filtreFiltre = new ComboBox<>();
        this.filtreFiltre.getItems().addAll("Par défaut", "Popularité", "Prix croissant", "Prix décroissant");
        this.filtreFiltre.setValue("Par défaut");
        this.filtreFiltre.setOnAction(e -> affichePageClient());

        this.ajouteRecherche = new Button("Ajouter au panier");
        this.ajouteCatalogue = new Button("Ajouter au panier");

        this.ajouteRecom = new Button("Ajouter au panier");
        
        this.ajouteRecherche.setOnAction(e -> {
            Livre livre = this.listeRecherche.getSelectionModel().getSelectedItem();
            Integer quantite = this.qteClient.getValue();
            ControleurAcheter controleur = new ControleurAcheter(this, quantite, livre);
            controleur.handle(e);
        });
        this.ajouteCatalogue.setOnAction(e -> {
            Livre livre = this.catalogue.getSelectionModel().getSelectedItem();
            Integer quantite = this.qteCatalogue.getValue();
            ControleurAcheter controleur = new ControleurAcheter(this, quantite, livre);
            controleur.handle(e);
        });
        this.ajouteRecom.setOnAction(e -> {
            Livre livre = this.recommande.getSelectionModel().getSelectedItem();
            Integer quantite = this.qteRecom.getValue();
            ControleurAcheter controleur = new ControleurAcheter(this, quantite, livre);
            controleur.handle(e);
        });

        this.boutonPanier = new Button("Panier");
        this.boutonPanier.setOnAction(e -> affichePagePanier());
        this.boutonRetourClient = new Button("Retour");
        this.boutonRetourClient.setOnAction(e -> affichePageClient());

        this.btnCommander = new Button("Finaliser la commande");
        this.btnCommander.setOnAction(new ControleurCommander(this));
        this.btnSupprSel = new Button("Supprimer la séléction");
        this.btnSupprSel.setOnAction(new ControleurSupprSel(this));
        this.btnViderPanier = new Button("Tout supprimer");
        this.btnViderPanier.setOnAction(e -> {
            this.panier = new Commande('0', '0', null, utilisateur);
            this.listeArticles = new ListView<>();
            this.listeArticles.getItems().addAll(this.getPanier().getDetailsCommande());
            this.affichePagePanier();
        });

        this.listeArticles = new ListView<>();


        this.livraisonDomicile = new RadioButton("Livraison à domicile");
        this. retraitMagasin = new RadioButton("Retrait en magasin");

        this.modeLivraison = new ToggleGroup();
        this.livraisonDomicile.setToggleGroup(this.modeLivraison);
        this.retraitMagasin.setToggleGroup(this.modeLivraison);
        
        // ============VENDEUR===============

        this.btnAjout = new Button("ajouter un livre à la librairie");
        this.btnStock = new Button("modifier les stocks d’un livre");
        this.btnTransfert = new Button("transférer un livre d’une autre librairie");
        this.listeRechercheVendeur = new ListView<>();
        this.rechercheV = new Button("Rechercher");


        // ============ADMINISTRATEUR===============

        this.btnRetourAdmin = new Button("Retour");
        this.btnLib = new Button("Librairie");
        this.btnVend = new Button("Vendeur");
        this.btnStats = new Button("Statistiques");
        this.btnAddLib = new Button("Ajouter une librairie");
        this.btnSuppLib = new Button("Supprimer une librairie");
        this.btnAddVendeur = new Button("Ajouter un vendeur");
        this.btnSuppVendeur = new Button("Supprimer un vendeur");
        this.btnLib.setOnAction(e -> this.affichePageAdminLib());
        this.btnVend.setOnAction(e -> this.affichePageAdminVendeur());
        this.btnStats.setOnAction(e -> this.affichePageAdminStats());
        this.btnRetourAdmin.setOnAction(e -> this.affichePageAdmin());

    }

    @Override
    public void start(Stage stage) {
        Pane root = new PageConnexion(boutonConnexion, boutonCreerUnCompte, identifiant, mdp, textErreurConnexion,
                false);
        this.scene = new Scene(root);
        this.stage = stage;
        this.stage.setWidth(320);
        this.stage.setHeight(300);
        stage.setScene(scene);
        stage.setTitle("LivreExpress");
        stage.show();
    }

    public void afficheConnexion() {
        Pane root = new PageConnexion(boutonConnexion, boutonCreerUnCompte, identifiant, mdp, textErreurConnexion,
                true);
        this.scene.setRoot(root);
        this.stage.setWidth(300);
        this.stage.setHeight(350);
    }

    public void afficheCreerCompte() {
        Pane root = new PageCreerCompte(boutonCreerLeCompte, textErreurCreerCompte, identifiant, mdpText, nom, prenom,
                adresse, ville, codePostal);
        this.scene.setRoot(root);
        this.stage.setWidth(300);
        this.stage.setHeight(600);
    }

    public void affichePageClient() {
        try {
            this.catalogue = this.clientBD.getCatalogue(this.filtreTheme.getSelectionModel().getSelectedItem(),
                    this.filtreFiltre.getSelectionModel().getSelectedItem());
            this.listeRecherche = this.clientBD.getRecherche(this.txtRecherche.getText());

            this.recommande = this.clientBD.onVousRecommandeIHM(this.utilisateur.getIdCli());
            Pane root = new PageClient(this);

            this.scene.setRoot(root);
            this.stage.setWidth(1450);
            this.stage.setHeight(800);
        } catch (SQLException e) {
            System.out.println("erreur");
        }
    }

    public void affichePagePanier() {
        this.listeArticles.getItems().addAll(this.getPanier().getDetailsCommande());
        Pane root = new PagePanierClient(this);
        this.scene.setRoot(root);
        this.stage.setWidth(750);
        this.stage.setHeight(550);
    }

    public void affichePageAdmin() {
        Pane root = new PageAdmin(this.boutonDeconnexion, this.btnLib, this.btnVend, this.btnStats);
        this.scene.setRoot(root);
        this.stage.setWidth(900);
        this.stage.setHeight(450);
    }

    public void affichePageAdminLib() {
        this.btnAddLib.setOnAction(new ControleurAdminModifLib(this, this.laConnexion));
        this.btnSuppLib.setOnAction(new ControleurAdminModifLib(this, this.laConnexion));
        Pane root = new PageAdminLibrairie(this.btnRetourAdmin, this.btnAddLib, this.btnSuppLib, this.magasinBD,
                this.laConnexion);
        this.scene.setRoot(root);
        this.stage.setWidth(1000);
        this.stage.setHeight(700);
    }

    public void affichePageAdminVendeur() {
        this.btnAddVendeur.setOnAction(new ControleurAdminModifVendeur(this, this.laConnexion));
        this.btnSuppVendeur.setOnAction(new ControleurAdminModifVendeur(this, this.laConnexion));
        Pane root = new PageAdminVendeur(this.btnRetourAdmin, this.btnAddVendeur, this.btnSuppVendeur, this.clientBD,
                this.laConnexion);
        this.scene.setRoot(root);
        this.stage.setWidth(1000);
        this.stage.setHeight(700);
    }

    public void affichePageAdminStats() {
        Pane root = new PageAdminStats(this.btnRetourAdmin, this.laConnexion);
        this.scene.setRoot(root);
        this.stage.setWidth(1400);
        this.stage.setHeight(1000);
    }

    public void affichePageVendeur() {
        this.rechercheV.setOnAction(e -> affichePageVendeur());
        try {
            this.btnTransfert.setOnAction(new ControleurVendeurTransfert(idMag, this.laConnexion));
            this.btnAjout.setOnAction(new ControleurVendeurAjoute(idMag, this.laConnexion));
            this.btnStock.setOnAction(new ControleurVendeurMajQte(idMag, this.laConnexion));
            this.listeRechercheVendeur = this.vendeurBD.getRecherche(this.txtRecherche.getText(), idMag);
            Pane root = new PageVendeur(this.boutonDeconnexion, 
            this.btnAjout, 
            this.btnStock, 
            this.btnTransfert, 
            this.rechercheV, 
            this.txtRecherche,
            this.listeRechercheVendeur,
            this.resumeCommande,
            this.supprimer,
            this.idAjout,
            this.ajouter,
            this.finaliser);

            this.scene.setRoot(root);
            this.stage.setWidth(1500);
            this.stage.setHeight(1000);
        } catch (SQLException e) {
            System.out.println("erreur");
        }

    }

    public void setUtilisateur(Client utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setConnexion(String ident, String mdp, String serveur, String nomBase) throws SQLException {
        try {
            this.laConnexion = new ConnexionMySQL();
            laConnexion.connecter(ident, mdp, serveur, nomBase);
            if (!laConnexion.isConnecte()) {
                throw new SQLException();
            }
            this.clientBD = new ClientBD(laConnexion);

            this.commandeBD = new CommandeBD(laConnexion);
            this.magasinBD = new MagasinBD(laConnexion);
            this.CBMagasins = new ComboBox<>();
            this.CBMagasins.getItems().addAll(this.magasinBD.listeDesMagasins());
            this.vendeurBD = new VendeurBD(laConnexion);
        } catch (ClassNotFoundException e) {
            System.out.println("classe non trouvé");
        }
    }

    public ConnexionMySQL getConnexion() {
        return laConnexion;
    }

    public Commande getPanier() {
        return panier;
    }

    public void setIdMag(int idMag) {
        this.idMag = idMag;
    }

    public void mauvaisMdp() {
        this.textErreurConnexion.setText("Mauvais indentifiants");
    }

    public void erreurCreerCompte() {
        this.textErreurCreerCompte.setText("Erreur veuiller réessayer");
    }

    public ClientBD getClientBD() {
        return clientBD;
    }

    public void setEnLigne(char enLigne) {
        this.panier.setEnLigne(enLigne);
    }

    public void setLivraison(char livraison) {
        this.panier.setLivraison(livraison);
    }

    public Magasin getMagasin() {
        return this.getPanier().getMagasin();
    }

    public void setMagasin(Magasin magasin) {
        this.panier.setMagasin(magasin);
    }

    public AdminBD getAdminBD() {
        return adminBD;
    }

    public MagasinBD getMagasinBD() {
        return magasinBD;
    }

    public Button getBoutonConnexion() {
        return boutonConnexion;
    }

    public Button getBoutonDeconnexion() {
        return boutonDeconnexion;
    }

    public Button getBoutonCreerUnCompte() {
        return boutonCreerUnCompte;
    }

    public Button getBoutonCreerLeCompte() {
        return boutonCreerLeCompte;
    }

    public TextField getIdentifiant() {
        return identifiant;
    }

    public PasswordField getMdp() {
        return mdp;
    }

    public TextField getMdpText() {
        return mdpText;
    }

    public TextField getNom() {
        return nom;
    }

    public TextField getPrenom() {
        return prenom;
    }

    public TextField getAdresse() {
        return adresse;
    }

    public TextField getVille() {
        return ville;
    }

    public TextField getCodePostal() {
        return codePostal;
    }

    public Label getTextErreurConnexion() {
        return textErreurConnexion;
    }

    public Label getTextErreurCreerCompte() {
        return textErreurCreerCompte;
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }

    public Client getUtilisateur() {
        return utilisateur;
    }

    public String getCompte() {
        return compte;
    }

    public ConnexionMySQL getLaConnexion() {
        return laConnexion;
    }

    public Button getAjouteRecherche() {
        return ajouteRecherche;
    }

    public Button getAjouteCatalogue() {
        return ajouteCatalogue;
    }

    public Button getAjouteRecom() {
        return ajouteRecom;
    }

    public Button getRecherche() {
        return recherche;
    }

    public ListView<Livre> getListeRecherche() {
        return listeRecherche;
    }

    public ListView<Livre> getCatalogue() {
        return catalogue;
    }

    public ListView<Livre> getRecommande() {
        return recommande;
    }

    public ComboBox<String> getFiltreTheme() {
        return filtreTheme;
    }

    public ComboBox<String> getFiltreFiltre() {
        return filtreFiltre;
    }

    public ComboBox<Integer> getQteCatalogue() {
        return qteCatalogue;
    }

    public ComboBox<Integer> getQteClient() {
        return qteClient;
    }

    public ComboBox<Integer> getQteRecom() {
        return qteRecom;
    }

    public TextField getTxtRecherche() {
        return txtRecherche;
    }

    public Button getBtnAjout() {
        return btnAjout;
    }

    public Button getBtnStock() {
        return btnStock;
    }

    public Button getBtnTransfert() {
        return btnTransfert;
    }

    public Button getAjouter() {
        return ajouter;
    }

    public int getIdMag() {
        return idMag;
    }

    public Button getBtnRetourAdmin() {
        return btnRetourAdmin;
    }

    public Button getBtnLib() {
        return btnLib;
    }

    public Button getBtnAddLib() {
        return btnAddLib;
    }

    public Button getBtnSuppLib() {
        return btnSuppLib;
    }

    public Button getBtnVend() {
        return btnVend;
    }

    public Button getBtnAddVendeur() {
        return btnAddVendeur;
    }

    public Button getBtnSuppVendeur() {
        return btnSuppVendeur;
    }

    public Button getBtnStats() {
        return btnStats;
    }

    public Button getBoutonPanier() {
        return boutonPanier;
    }

    public Button getBoutonRetourClient() {
        return boutonRetourClient;
    }

    public Button getBtnCommander() {
        return btnCommander;
    }

    public Button getBtnSupprSel() {
        return btnSupprSel;
    }

    public Button getBtnViderPanier() {
        return btnViderPanier;
    }

    public ListView<DetailCommande> getListeArticles() {
        return listeArticles;
    }

    public CommandeBD getCommandeBD() {
        return commandeBD;
    }

    public void nouveauPanier() {
        this.panier = new Commande('0', '0', null, utilisateur);
    }

    public ComboBox<Magasin> getCBMagasins() {
        return CBMagasins;
    }

    public ToggleGroup getModeLivraison() {
        return modeLivraison;
    }

    public RadioButton getLivraisonDomicile() {
        return livraisonDomicile;
    }

    public RadioButton getRetraitMagasin() {
        return retraitMagasin;
    }

}