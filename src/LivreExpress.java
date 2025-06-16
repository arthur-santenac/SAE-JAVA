import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


 
public class LivreExpress extends Application {
    
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
    }
    
    @Override
    public void start(Stage stage) {
        Pane root = new PageClient();
        this.scene = new Scene(root);
        this.stage = stage;
        stage.setScene(scene);
        stage.setTitle("LivreExpress");
        stage.show();
    }
 
    public void afficheFenetre1() {

    }
    
    public void affichePageClient() {
        Pane root = new PageClient();   
        this.scene.setRoot(root);
        this.stage.setWidth(1000);
        this.stage.setHeight(600);
    }
}
