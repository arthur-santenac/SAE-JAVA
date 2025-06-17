import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PageConnexion extends BorderPane {
  
    private boolean estConnecteBD;
    private Button connexion;
    private TextField tfEmail;
    private TextField tfMdp;
    
    public PageConnexion(Button connexion, TextField email, TextField mdp, boolean estConnecteBD){
        this.connexion = connexion;
        this.tfEmail = email;
        this.tfMdp = mdp;
        this.estConnecteBD = estConnecteBD;
        this.setTop(this.entete());
        this.setCenter(this.centre());
    }

    private BorderPane entete() {
        BorderPane entete = new BorderPane();
        Text titre = new Text();
        titre.setText("Livre Express");
        titre.setFont(Font.font("Arial", FontWeight.MEDIUM, 32));
        entete.setLeft(titre);
        entete.setPadding(new Insets(10));
        entete.setBackground( new Background(new BackgroundFill(Color.GRAY,null,null)));
        return entete;
    }

    private GridPane centre(){
        GridPane centre = new GridPane();
        this.tfEmail.setPromptText("Entrez une adresse email");
        this.tfMdp.setPromptText("Entrez une mot de passe");
        Button btnCreerCompte = new Button("Créer un compte");
        this.connexion.setPrefWidth(200);
        btnCreerCompte.setPrefWidth(200);
        centre.add(this.tfEmail, 0, 0, 4, 2);
        centre.add(this.tfMdp, 0, 2, 4, 2);
        centre.add(this.connexion, 0, 4, 4, 2);
        centre.add(btnCreerCompte, 0, 8, 4, 2);
        centre.setAlignment(Pos.BASELINE_CENTER);
        centre.setHgap(10);
        centre.setVgap(20);
        centre.setPadding(new Insets(50));
        return centre;
    }
}