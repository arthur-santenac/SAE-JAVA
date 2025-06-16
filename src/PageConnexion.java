import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PageConnexion extends BorderPane {
  
    

    private TextField tfEmail;

    private TextField tfMdp;
    
    public PageConnexion(){
        this.tfEmail = new TextField();
        this.tfMdp = new TextField();
        this.setTop(this.entete());
        this.setCenter(this.centre());
    }


    
    private BorderPane entete() {
        BorderPane entete = new BorderPane();
        Text titre = new Text();
        titre.setText("Livre Express");
        titre.setFont(Font.font("Arial", FontWeight.MEDIUM, 32));
        entete.setLeft(titre);
        return entete;
    }

    private VBox centre(){
        VBox centre = new VBox(30);
        this.tfEmail.setPromptText("Entrez votre adresse email");
        this.tfMdp.setPromptText("Entrez votre mot de passe");
        Button btnConnexion = new Button("Connexion");
        btnConnexion.setBackground( new Background( new BackgroundFill(Color.ALICEBLUE,null,null)));
        btnConnexion.setStyle("-fx-border-color:black");
        Button btnCreerCompte = new Button("Créer un compte");
        centre.getChildren().addAll(this.tfEmail, this.tfMdp, btnConnexion, btnCreerCompte);
        btnConnexion.setPadding(new Insets(0, 0, 0, 30));
        centre.setAlignment(Pos.BASELINE_CENTER);
        return centre;
    }



}


