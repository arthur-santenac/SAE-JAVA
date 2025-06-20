import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javax.swing.plaf.synth.Region;

public class PageVendeur extends BorderPane {
    private Button deconnexion;
    private Button btnAjout;
    private Button btnStock;
    private Button btnTransfert;



    private Button recherche;
    private ListView<String> listeRecherche;
    private TextField txtRecherche;

    private TextField idAjout;
    private Button ajouter;
    private Button finaliser;
    private Button supprimer;
    private ListView<DetailCommande> resumeCommande;

    public PageVendeur(Button deconnexion,  
    
    Button btnAjout, 
    Button btnStock, 
    Button btnTransfert,
    Button recherche, 
    TextField txtRecherche, 
    ListView<String> listeRecherche, 
    ListView<DetailCommande> resumeCommande,  
    Button supprimer , 
    TextField idAjout, 
    Button ajouter, 
    Button finaliser) {

        super();
        this.deconnexion = deconnexion;
        this.btnAjout = btnAjout;
        this.btnStock = btnStock;
        this.btnTransfert = btnTransfert;
        this.idAjout = idAjout;
        this.ajouter = ajouter;
        this.finaliser = finaliser;
        this.recherche = recherche;
        this.txtRecherche = txtRecherche;
        this. listeRecherche = listeRecherche;

        this.idAjout = idAjout;
        this.ajouter = ajouter;
        this.finaliser = finaliser;
        this.supprimer = supprimer;
        this.resumeCommande = resumeCommande;

        this.setTop(this.top());
        this.setLeft(this.left());
        this.setCenter(this.center());
        this.setRight(this.right());

    }

    private BorderPane top() {
        BorderPane entete = new BorderPane();
        Label titre = new Label("Livre Express - Vendeur");
        titre.setStyle("-fx-text-fill: white;");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        entete.setLeft(titre);
        entete.setRight(this.deconnexion);
        entete.setPadding(new Insets(10));
        entete.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        return entete;
    }

    public VBox left() {
        Label bonjour = new Label("Bonjour !");

        VBox left = new VBox(10, bonjour, this.btnAjout, this.btnStock, this.btnTransfert);
        left.setStyle("-fx-padding: 10;");
        return left;
    }

    public VBox center() {
        BorderPane teteLeft = new BorderPane();
        teteLeft.setLeft(txtRecherche);
        teteLeft.setRight(recherche);


        listeRecherche.setPrefHeight(400);
        listeRecherche.setPrefWidth(400);
        
        VBox left = new VBox(10, teteLeft, listeRecherche);    
        left.setPadding(new Insets(10));
        left.setFillWidth(true);
        return left;
    }

    public VBox right() {
        TextField champId = this.idAjout;
        HBox ajoutZone = new HBox(champId, this.ajouter);
        ajoutZone.setAlignment(Pos.CENTER_RIGHT);

        Label resumeTitre = new Label("Résumé de la commande");
        resumeTitre.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-padding: 5;");
        ListView<DetailCommande> listeCommande = this.resumeCommande;

        VBox resumeBox = new VBox(resumeTitre, listeCommande);
        resumeBox.setStyle("-fx-border-color: black;");
        resumeBox.setPrefSize(300, 400);

        Button btnFinaliser = this.finaliser;
        btnFinaliser.setStyle("-fx-background-color: #666; -fx-text-fill: white;");

        Button btnSupprimer = this.supprimer;
        btnFinaliser.setStyle("-fx-background-color: #666; -fx-text-fill: white;");

        VBox rightPanel = new VBox(ajoutZone, resumeBox, btnSupprimer, btnFinaliser);
        rightPanel.setPadding(new Insets(20));
        rightPanel.setAlignment(Pos.TOP_RIGHT);
        return rightPanel;
    }

    
}
