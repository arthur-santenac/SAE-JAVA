import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javax.swing.plaf.synth.Region;

public class PageVendeur extends BorderPane {
    private Button deconnexion;
    private Label bonjour;
    private Button btnAjout;
    private Button btnStock;
    private Button btnTransfert;
    private TextField idAjout;
    private Button ajouter;
    private Button finaliser;
    public PageVendeur(Button deconnexion, Label bonjour, Button btnAjout, Button btnStock, Button btnTransfert, TextField idAjout, Button ajouter, Button finaliser) {

        super();
        this.deconnexion = deconnexion;
        this.bonjour = bonjour;
        this.btnAjout = btnAjout;
        this.btnStock = btnStock;
        this.btnTransfert = btnTransfert;
        this.idAjout = idAjout;
        this.ajouter = ajouter;
        this.finaliser = finaliser;


        this.setTop(this.top());
        this.setLeft(this.left());
        this.setCenter(this.center());
        this.setRight(this.right());

    }

    public HBox top() {
        Label titre = new Label("Livre Express");
        Button btnDeconnexion = new Button("deconnexion");
        HBox top = new HBox(20, titre, btnDeconnexion);
        top.setStyle("-fx-padding: 10; -fx-alignment: center_left; -fx-background-color: #666; -fx-spacing: 20;");
        titre.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
        btnDeconnexion.setStyle("-fx-background-color: #ddd;");
        return top;
    }

    public VBox left() {

        VBox left = new VBox(10, this.bonjour, this.btnAjout, this.btnStock, this.btnTransfert);
        left.setStyle("-fx-padding: 10;");
        return left;
    }

    public VBox center() {
        VBox center = new VBox();
        return center;
    }

    public VBox right() {
        TextField champId = new TextField();
        champId.setPromptText("id de l’article à ajouter");
        champId.setPrefWidth(200);

        Button btnAjouterArticle = new Button("ajouter");
        HBox ajoutZone = new HBox(10, champId, btnAjouterArticle);
        ajoutZone.setAlignment(Pos.CENTER_RIGHT);

        Label resumeTitre = new Label("Résumé de la commande");
        resumeTitre.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-padding: 5;");
        ListView<String> listeCommande = new ListView<>();

        VBox resumeBox = new VBox(resumeTitre, listeCommande);
        resumeBox.setStyle("-fx-border-color: black;");
        resumeBox.setPrefSize(300, 400);

        Button btnFinaliser = new Button("finaliser commande");
        btnFinaliser.setStyle("-fx-background-color: #666; -fx-text-fill: white;");

        VBox rightPanel = new VBox(15, ajoutZone, resumeBox, btnFinaliser);
        rightPanel.setPadding(new Insets(20));
        rightPanel.setAlignment(Pos.TOP_RIGHT);
        return rightPanel;
    }
}
