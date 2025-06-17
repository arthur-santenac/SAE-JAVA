import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class PageConnexion extends BorderPane {
  
    private boolean estConnecteBD;
    private Button connexion;
    private TextField tfEmail;
    private TextField tfMdp;
    private Label textErreur;
    
    public PageConnexion(Button connexion, TextField email, TextField mdp, Label erreurIdentTF, boolean estConnecteBD){
        this.connexion = connexion;
        this.connexion.setPrefWidth(200);
        this.connexion.setPrefHeight(40);
        this.tfEmail = email;
        this.tfMdp = mdp;
        this.textErreur = erreurIdentTF;
        this.tfEmail.setText("");
        this.tfMdp.setText("");
        this.textErreur.setText("");
        this.estConnecteBD = estConnecteBD;
        this.setTop(this.entete());
        this.setCenter(this.centre());
    }

    private BorderPane entete() {
        if(! this.estConnecteBD){
            BorderPane entete = new BorderPane();
            Text titre = new Text();
            titre.setText("Connexion BD");
            titre.setFont(Font.font("Arial", FontWeight.MEDIUM, 32));
            entete.setLeft(titre);
            entete.setPadding(new Insets(10));
            entete.setBackground( new Background(new BackgroundFill(Color.GRAY,null,null)));
            return entete;
        }
        else{
            BorderPane entete = new BorderPane();
            Text titre = new Text();
            titre.setText("Livre Express");
            titre.setFont(Font.font("Arial", FontWeight.MEDIUM, 32));
            entete.setLeft(titre);
            entete.setPadding(new Insets(10));
            entete.setBackground( new Background(new BackgroundFill(Color.GRAY,null,null)));
            return entete;
        }
        
    }

    private GridPane centre(){
        if(! this.estConnecteBD){
            GridPane centre = new GridPane();
            this.tfEmail.setPromptText("Entrez votre identifiant BD");
            this.tfMdp.setPromptText("Entrez votre mot de passe BD");
            centre.add(this.tfEmail, 0, 0, 4, 2);
            centre.add(this.tfMdp, 0, 2, 4, 2);
            centre.add(this.textErreur, 0, 4, 4, 2);
            centre.add(this.connexion, 0, 5, 4, 2);
            centre.setAlignment(Pos.BASELINE_CENTER);
            centre.setHgap(10);
            centre.setVgap(20);
            centre.setPadding(new Insets(50));
            return centre;
        }else{
            GridPane centre = new GridPane();
            Text textCo = new Text("CONNEXION");
            textCo.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
            textCo.setTextAlignment(TextAlignment.CENTER);
            this.tfEmail.setPromptText("Entrez une adresse email");
            this.tfMdp.setPromptText("Entrez une mot de passe");
            this.connexion.setPrefWidth(200);
            this.connexion.setPrefHeight(40);
            Button btnCreerCompte = new Button("Créer un compte");
            btnCreerCompte.setPrefWidth(200);

            centre.add(textCo, 0, 0, 4, 2);
            centre.add(this.tfEmail, 0, 2, 4, 2);
            centre.add(this.tfMdp, 0, 4, 4, 2);
            centre.add(this.textErreur, 0, 5, 4, 2);
            centre.add(this.connexion, 0, 6, 4, 2);
            centre.add(btnCreerCompte, 0, 11, 4, 2);

            centre.setAlignment(Pos.BASELINE_CENTER);
            centre.setHgap(10);
            centre.setVgap(20);
            centre.setPadding(new Insets(50));
            return centre;
        }
        
    }
}