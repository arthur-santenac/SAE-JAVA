import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


 
public class LivreExpress extends Application {
    
    private Button boutonConnexion;
    private Button boutonDeconnexion;
    private TextField email;
    private TextField motDePasse; 


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
        this.motDePasse = new TextField();
        this.boutonConnexion = new Button("Connexion");
        this.boutonDeconnexion = new Button("Déconnexion");
        boutonDeconnexion.setStyle("-fx-background-color: #ddd;");
        ControleurQuitter controleurQuitter = new ControleurQuitter(this);
        this.boutonConnexion.setOnAction(controleurQuitter);
        this.boutonDeconnexion.setOnAction(controleurQuitter);
    }
    
    @Override
    public void start(Stage stage) {
        Pane root = new PageConnexion(boutonConnexion);
        this.scene = new Scene(root);
        this.stage = stage;
        stage.setScene(scene);
        stage.setTitle("LivreExpress");
        stage.show();
    }
 
    public void afficheConnexion() {
        Pane root = new PageConnexion(boutonConnexion);
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
