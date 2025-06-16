import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

        boutonDeconnexion.setStyle("-fx-background-color: #ddd;");

        this.boutonConnexion.setBackground( new Background( new BackgroundFill(Color.ALICEBLUE,null,null)));
        this.boutonConnexion.setStyle("-fx-border-color:black");
        this.boutonConnexion.setPadding(new Insets(0, 0, 0, 30));

        ControleurConnexion controleurConnexion = new ControleurConnexion(this, email.getText(), mdp.getText());
        this.boutonConnexion.setOnAction(controleurConnexion);
        this.boutonDeconnexion.setOnAction(controleurConnexion);
    }
    
    @Override
    public void start(Stage stage) {
        Pane root = new PageConnexion(boutonConnexion, email, mdp);
        this.scene = new Scene(root);
        this.stage = stage;
        stage.setScene(scene);
        stage.setTitle("LivreExpress");
        stage.show();
    }
 
    public void afficheConnexion() {
        Pane root = new PageConnexion(boutonConnexion, email, mdp);
        this.scene.setRoot(root);
        this.stage.setWidth(400);
        this.stage.setHeight(200);
    }
    
    public void affichePageClient() {
        Pane root = new PageClient(boutonDeconnexion);   
        this.scene.setRoot(root);
        this.stage.setWidth(1000);
        this.stage.setHeight(600);
    }
}