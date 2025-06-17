import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class PageCreerCompte extends BorderPane{
    
    private Button boutonCreerLeCompte;
    private TextField email;
    private TextField mdp;
    private TextField nom;
    private TextField prenom;
    private TextField adresse;
    private TextField ville;
    private TextField codePostal;
    private Label erreur;
    

    public PageCreerCompte(Button boutonCreerLeCompte, Label erreur, TextField email, TextField mdp, TextField nom, TextField prenom, TextField adresse, TextField ville, TextField codePostal) {
        this.boutonCreerLeCompte = boutonCreerLeCompte;
        this.email = email;
        this.mdp = mdp;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.ville = ville;
        this.codePostal = codePostal;
        this.erreur = erreur;
        this.email.setText("");
        this.mdp.setText("");
        this.nom.setText("");
        this.prenom.setText("");
        this.adresse.setText("");
        this.ville.setText("");
        this.codePostal.setText("");
        this.setTop(this.entete());
        this.setCenter(this.centre());
    }

    private BorderPane entete() {
        BorderPane entete = new BorderPane();
        Label titre = new Label();
        titre.setText("Livre Express");
        titre.setStyle("-fx-text-fill: white; -fx-font-size: 24px;");
        entete.setLeft(titre);
        entete.setPadding(new Insets(10));
        entete.setBackground( new Background(new BackgroundFill(Color.GRAY,null,null)));
        return entete;
    }

    private GridPane centre() {

        GridPane centre = new GridPane();
        Text textCo = new Text("CREER UN COMPTE");
        textCo.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        textCo.setTextAlignment(TextAlignment.CENTER);
        this.email.setPromptText("Entrez une adresse email");
        this.mdp.setPromptText("Entrez un mot de passe");
        this.nom.setPromptText("Entrez votre nom");
        this.prenom.setPromptText("Entrez votre prenom");
        this.adresse.setPromptText("Entrez votre adresse");
        this.ville.setPromptText("Entrez votre ville");
        this.codePostal.setPromptText("Entrez votre code postal");
        this.boutonCreerLeCompte.setPrefWidth(200);

        centre.add(textCo, 0, 0, 4, 2);
        centre.add(this.email, 0, 2, 4, 2);
        centre.add(this.mdp, 0, 4, 4, 2);
        centre.add(this.nom, 0, 6, 4, 2);
        centre.add(this.prenom, 0, 8, 4, 2);
        centre.add(this.adresse, 0, 10, 4, 2);
        centre.add(this.ville, 0, 12, 4, 2);
        centre.add(this.codePostal, 0, 14, 4, 2);
        centre.add(this.erreur, 0, 16, 4, 2);
        centre.add(this.boutonCreerLeCompte, 0, 18, 4, 2);

        centre.setAlignment(Pos.BASELINE_CENTER);
        centre.setHgap(10);
        centre.setVgap(20);
        centre.setPadding(new Insets(30));
        return centre;
        
    }

}
