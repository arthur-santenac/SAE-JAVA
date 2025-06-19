import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LivreExpress extends Application {

    private ClientBD clientBD;
    private AdminBD adminBD;
    private MagasinBD magasinBD;

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

    private Button btnAjouterPanierRecherche;
    private Button recherche;
    private ListView<Livre> listeRecherche;
    private ListView<Livre> catalogue;
    private ComboBox<String> filtreTheme;
    private ComboBox<String> filtreFiltre;
    private ComboBox<Integer> qteCatalogue;
    private ComboBox<Integer> qteClient;
    private Button ajouteCatalogue;
    private TextField txtRecherche;

    // ============VENDEUR===============
    private Button btnAjout;
    private Button btnStock;
    private Button btnTransfert;
    private Button ajouter;
    private Button finaliserCommande;
    private Label bonjour;
    private int idMag;
    private TextField idAjouter;

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
        ControleurCreerCompte controleurCreerCompte = new ControleurCreerCompte(this, this.identifiant, this.mdpText, this.nom, this.prenom, this.adresse, this.ville, this.codePostal);

        this.boutonConnexion.setOnAction(controleurConnexion);
        this.boutonDeconnexion.setOnAction(controleurConnexion);
        this.boutonCreerUnCompte.setOnAction(controleurPageCreerCompte);
        this.boutonCreerLeCompte.setOnAction(controleurCreerCompte);

        // ============CLIENT===============

        this.btnAjouterPanierRecherche = new Button("Ajouter au panier");
        this.recherche = new Button("Rechercher");
        this.recherche.setOnAction(e -> affichePageClient());
        this.txtRecherche = new TextField();
        this.txtRecherche.setPromptText("Entrez le nom d'un livre");
        this.txtRecherche.setMaxWidth(Double.MAX_VALUE);
        this.catalogue = new ListView<>();
        this.listeRecherche = new ListView<>();
        this.filtreTheme = new ComboBox<>();
        this.filtreTheme.getItems().addAll("Tout", "Arts et Loisirs", "Histoire et Géographie", "Informatique, généralités", "Langues", "Littérature", "Philosophie et psychologie", "Religion", "Science naturelles et mathématiques", "Sciences sociales", "Technologie et sciences appliqués");
        this.filtreTheme.setValue("Tout");
        this.filtreTheme.setOnAction(e -> affichePageClient());
        this.filtreFiltre = new ComboBox<>();
        this.filtreFiltre.getItems().addAll("Par défaut", "Popularité", "Prix croissant", "Prix décroissant");
        this.filtreFiltre.setValue("Par défaut");
        this.filtreFiltre.setOnAction(e -> affichePageClient());
        this.qteClient = new ComboBox<>();
        this.qteClient.getItems().addAll(1, 2, 3, 4, 5);
        this.qteClient.setValue(1);
        this.qteCatalogue = new ComboBox<>();
        this.qteCatalogue.getItems().addAll(1, 2, 3, 4, 5);
        this.qteCatalogue.setValue(1);
        this.ajouteCatalogue = new Button("Ajouter au panier");
        this.ajouteCatalogue.setOnAction(new ControleurBoutonCatalogue(this, this.qteCatalogue.getValue(), this.catalogue.getSelectionModel().getSelectedItem()));
        
        

        // ============VENDEUR===============




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
        Pane root = new PageConnexion(boutonConnexion, boutonCreerUnCompte, identifiant, mdp, textErreurConnexion, false);
        this.scene = new Scene(root);
        this.stage = stage;
        this.stage.setWidth(320);
        this.stage.setHeight(300);
        stage.setScene(scene);
        stage.setTitle("LivreExpress");
        stage.show();
    }

    public void afficheConnexion() {
        Pane root = new PageConnexion(boutonConnexion, boutonCreerUnCompte, identifiant, mdp, textErreurConnexion, true);
        this.scene.setRoot(root);
        this.stage.setWidth(300);
        this.stage.setHeight(350);
    }

    public void afficheCreerCompte() {
        Pane root = new PageCreerCompte(boutonCreerLeCompte, textErreurCreerCompte, identifiant, mdpText, nom, prenom, adresse, ville, codePostal);
        this.scene.setRoot(root);
        this.stage.setWidth(300);
        this.stage.setHeight(600); 
    }

    public void affichePageClient() {
        try {
            this.catalogue = this.clientBD.getCatalogue(this.filtreTheme.getSelectionModel().getSelectedItem(), this.filtreFiltre.getSelectionModel().getSelectedItem());
            this.listeRecherche = this.clientBD.getRecherche(this.txtRecherche.getText());
            Pane root = new PageClient(boutonDeconnexion, recherche, btnAjouterPanierRecherche, ajouteCatalogue, txtRecherche, listeRecherche, catalogue, filtreTheme, filtreFiltre, qteClient, qteCatalogue);
            this.scene.setRoot(root);
            this.stage.setWidth(1550);
            this.stage.setHeight(850);
        } catch (SQLException e) {
            System.out.println("erreur");
        }
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
        Pane root = new PageAdminLibrairie(this.btnRetourAdmin,this.btnAddLib, this.btnSuppLib, this.magasinBD, this.laConnexion);
        this.scene.setRoot(root);
        this.stage.setWidth(1000);
        this.stage.setHeight(700);
    }

    public void affichePageAdminVendeur() {
        this.btnAddVendeur.setOnAction(new ControleurAdminModifVendeur(this, this.laConnexion));
        this.btnSuppVendeur.setOnAction(new ControleurAdminModifVendeur(this, this.laConnexion));
        Pane root = new PageAdminVendeur(this.btnRetourAdmin,this.btnAddVendeur, this.btnSuppVendeur, this.clientBD, this.laConnexion);
        this.scene.setRoot(root);
        this.stage.setWidth(1000);
        this.stage.setHeight(700);
    }

    public void affichePageAdminStats() {
        Pane root = new PageAdminStats(this.btnRetourAdmin, this.laConnexion);
        this.scene.setRoot(root);
        this.stage.setWidth(1000);
        this.stage.setHeight(700);
    }

    public void affichePageVendeur() {
        this.bonjour = new Label("Bonjour !");
        this.btnAjout = new Button("ajouter un livre à la librairie");
        this.btnAjout.setOnAction(new ControleurVendeurAjoute(idMag, this.laConnexion));
        this.btnStock = new Button("modifier les stocks d’un livre");
        this.btnStock.setOnAction(new ControleurVendeurMajQte(idMag, this.laConnexion));
        
        this.btnTransfert = new Button("transférer un livre d’une autre librairie");
        this.btnTransfert.setOnAction(new ControleurVendeurTransfert(idMag, this.laConnexion));
        Pane root = new PageVendeur(this.boutonDeconnexion, this.bonjour, this.btnAjout, this.btnStock, this.btnTransfert, this.idAjouter, this.ajouter, this.finaliserCommande);
        this.scene.setRoot(root);
        this.stage.setWidth(1500);
        this.stage.setHeight(1000);

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

    public void setMagasin(Magasin magasin) {
        this.panier.setMagasin(magasin);
    }

}