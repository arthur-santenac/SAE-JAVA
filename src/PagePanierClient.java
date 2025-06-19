import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PagePanierClient extends BorderPane {

    private LivreExpress appli;
    private Label lblNbArticles = new Label("");
    private Label lblPrixTotal = new Label(""); 
    

    public PagePanierClient(LivreExpress appli) {
        this.appli = appli;

        mettreAJourCompteur();

        this.setTop(this.entete());
        this.setCenter(this.centre());
    }

    private BorderPane entete() {
        BorderPane top = new BorderPane();
        Label titre = new Label("Livre Express");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titre.setStyle("-fx-text-fill: white;");

        top.setLeft(titre);
        top.setRight(this.appli.getBoutonRetourClient());
        top.setPadding(new Insets(10));
        top.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));

        return top;
    }

    private Pane centre() {
        BorderPane centre = new BorderPane();
        centre.setPadding(new Insets(30, 40, 0, 40));

        VBox gauche = new VBox(10);
        gauche.setPrefWidth(420);

        Label titrePanier = new Label("Votre panier");
        titrePanier.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        this.appli.getBtnSupprSel().setPrefWidth(180);
        this.appli.getBtnViderPanier().setPrefWidth(180);
        HBox boutons = new HBox(10, this.appli.getBtnSupprSel(), this.appli.getBtnViderPanier());

        this.appli.getListeArticles().setPrefSize(400, 320);
        gauche.getChildren().addAll(titrePanier, this.appli.getListeArticles(), boutons);

        VBox droite = new VBox(10);
        droite.setPrefWidth(200);
        droite.setPadding(new Insets(0, 0, 0, 40));

        VBox recap = new VBox(10, lblNbArticles, lblPrixTotal);
        recap.setPadding(new Insets(10));
        recap.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        Label lblMagasin = new Label("Magasins :");
        Label lblModeLivraison = new Label("Mode de Livraison :");        

        this.appli.getBtnCommander().setPrefWidth(180);
        droite.getChildren().addAll(recap, lblMagasin, this.appli.getCBMagasins(), lblModeLivraison, this.appli.getLivraisonDomicile(), this.appli.getRetraitMagasin(), this.appli.getBtnCommander());

        HBox contenu = new HBox(40, gauche, droite);
        centre.setCenter(contenu);

        return centre;
    }

    private void mettreAJourCompteur() {
        int taille = this.appli.getPanier().size();
        if (taille <= 1) {
            lblNbArticles.setText(taille + " article");
        } else {
            lblNbArticles.setText(taille + " articles");
        }
    }

}
