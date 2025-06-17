import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


 
public class LivreExpress extends Application {
    
    private Button boutonConnexion;
    private Button boutonDeconnexion;
    private TextField email;
    private TextField mdp; 


    private Scene scene;
    private Stage stage;
    private Client utilisateur;
    private String compte;
    private ConnexionMySQL laConnexion = null;


 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(LivreExpress.class, args);
    }
    
    @Override
    public void init(){

        this.email = new TextField();
        this.mdp = new TextField();
        this.boutonConnexion = new Button("Connexion");
        this.boutonDeconnexion = new Button("Déconnexion");
        this.boutonConnexion.setPadding(new Insets(0, 0, 0, 30));

        ControleurConnexion controleurConnexion = new ControleurConnexion(this, this.email, this.mdp);

        this.boutonConnexion.setOnAction(controleurConnexion);

        this.boutonDeconnexion.setOnAction(controleurConnexion);

        // Label bonjour = new Label("Bonjour " + nomVendeur + " !");
        // Button btnAjout = new Button("ajouter un livre à la librairie");
        // btnAjout.setOnAction(new ControleurVendeurAjoute(idMag, this.appli.getConnexion));
        // Button btnStock = new Button("modifier les stocks d’un livre");
        // btnAjout.setOnAction(new ControleurVendeurMajQte(idMag, this.appli.getConnexion));
        // Button btnTransfert = new Button("transférer un livre d’une autre librairie");
       

    }
    
    @Override
    public void start(Stage stage) {
        Pane root = new PageConnexion(boutonConnexion, email, mdp, false);
        this.scene = new Scene(root);
        this.stage = stage;
        this.stage.setWidth(350);
        this.stage.setHeight(400);
        stage.setScene(scene);
        stage.setTitle("LivreExpress");
        stage.show();
    }
 
    public void afficheConnexion() {
        Pane root = new PageConnexion(boutonConnexion, email, mdp, true);
        this.scene.setRoot(root);
        this.stage.setWidth(350);
        this.stage.setHeight(400);
    }
    
    public void affichePageClient() {
        Pane root = new PageClient(boutonDeconnexion);   
        this.scene.setRoot(root);
        this.stage.setWidth(1000);
        this.stage.setHeight(600);
    }


    public void affichePageAdmin() {
        Pane root = new PageAdmin(boutonDeconnexion);   
        this.scene.setRoot(root);
        this.stage.setWidth(1000);
        this.stage.setHeight(600);
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
        } catch (ClassNotFoundException e) {
            System.out.println("classe non trouvé");
        }
        
    }

    public ConnexionMySQL getConnexion() {
        return laConnexion;
    }
}