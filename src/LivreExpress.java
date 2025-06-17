import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    // ============CONNEXION / CREER COMPTE===============
    private Button boutonConnexion;
    private Button boutonDeconnexion;
    private Button boutonCreerUnCompte;
    private Button boutonCreerLeCompte;
    private TextField identifiant;
    private PasswordField mdp; 
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

    // ============VENDEUR===============
    private Button btnAjout;
    private Button btnStock;
    private Button btnTransfert;
    private Button ajouter;
    private Button finaliserCommande;
    private Label bonjour;
    private int idMag;
    private TextField idAjouter;

    // ============ADMINISTRATEUR===============
    private Button btnRetourAdmin;
    private Button btnLib;
    private AdminBD adminBD;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(LivreExpress.class, args);
    }

    @Override
    public void init() {

        this.identifiant = new TextField();
        this.mdp = new PasswordField();
        this.adresse = new TextField();
        this.nom = new TextField();
        this.prenom = new TextField();
        this.adresse = new TextField();
        this.ville = new TextField();
        this.codePostal = new TextField();
        this.email = new TextField();
        this.mdp = new 
        this.textErreurConnexion = new Label();
        this.textErreurCreerCompte = new Label();
        this.boutonConnexion = new Button("Connexion");
        this.boutonDeconnexion = new Button("Déconnexion");
        this.boutonCreerUnCompte = new Button("Créer un compte");
        this.boutonCreerLeCompte = new Button("Créer le compte");

        ControleurConnexion controleurConnexion = new ControleurConnexion(this, this.identifiant, this.mdp);
        ControleurPageCreerCompte controleurPageCreerCompte = new ControleurPageCreerCompte(this);
        ControleurCreerCompte controleurCreerCompte = new ControleurCreerCompte(this, this.identifiant, this.mdp, this.nom, this.prenom, this.adresse, this.ville, this.codePostal);

        this.boutonConnexion.setOnAction(controleurConnexion);
        this.boutonDeconnexion.setOnAction(controleurConnexion);
        this.boutonCreerUnCompte.setOnAction(controleurPageCreerCompte);
        this.boutonCreerLeCompte.setOnAction(controleurCreerCompte);


        this.bonjour = new Label("Bonjour !");
        this.btnAjout = new Button("ajouter un livre à la librairie");
        this.btnAjout.setOnAction(new ControleurVendeurAjoute(idMag, this.laConnexion));
        this.btnStock = new Button("modifier les stocks d’un livre");
        this.btnAjout.setOnAction(new ControleurVendeurMajQte(idMag, this.laConnexion));
        this.btnTransfert = new Button("transférer un livre d’une autre librairie");


        // ============ADMINISTRATEUR===============
        this.btnLib = new Button("Librairie");
        this.btnRetourAdmin = new Button("Retour");
        this.btnLib.setOnAction(new ControleurAdminLibrairie(this));
        this.btnRetourAdmin.setOnAction(new ControleurRetourAdmin(this));

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
        Pane root = new PageCreerCompte(boutonCreerLeCompte, textErreurCreerCompte, identifiant, mdp, nom, prenom, adresse, ville, codePostal);
        this.scene.setRoot(root);
        this.stage.setWidth(300);
        this.stage.setHeight(600); 
    }

    public void affichePageClient() {
        Pane root = new PageClient(boutonDeconnexion);
        this.scene.setRoot(root);
        this.stage.setWidth(1500);
        this.stage.setHeight(600);
    }

    public void affichePageAdmin() {
        Pane root = new PageAdmin(this.boutonDeconnexion, this.btnLib);
        this.scene.setRoot(root);
        this.stage.setWidth(900);
        this.stage.setHeight(450);
    }

    public void affichePageAdminLib() {
        Pane root = new PageAdminLibrairie(this.btnRetourAdmin);
        this.scene.setRoot(root);
        this.stage.setWidth(900);
        this.stage.setHeight(450);
    }

    public void affichePageVendeur() {
        Pane root = new PageVendeur(boutonDeconnexion, bonjour, btnAjout, btnStock, btnTransfert, idAjouter, ajouter, finaliserCommande);
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

}