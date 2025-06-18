import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PagePanierClient extends BorderPane {

    private ListView<String> listeArticles;
    private Label lblNbArticles;
    private Label lblPrixTotal;
    private Button btnCommander;
    private Button btnSupprSel;
    private Button btnViderPanier;
    private Button boutonRetour;
    

    public PagePanierClient(
            Button boutonRetour,
            ListView<String> listeArticles,
            Label lblNbArticles,
            Label lblPrixTotal,
            Button btnCommander,
            Button btnSupprSel,
            Button btnViderPanier,
            ObservableList<String> donneesPanier) {
        this.boutonRetour = boutonRetour;
        this.listeArticles = listeArticles;
        this.lblNbArticles = lblNbArticles;
        this.lblPrixTotal = lblPrixTotal;
        this.btnCommander = btnCommander;
        this.btnSupprSel = btnSupprSel;
        this.btnViderPanier = btnViderPanier;

        this.listeArticles.setItems(donneesPanier);
        this.listeArticles.setPrefSize(400, 320);
        mettreAJourCompteur(donneesPanier);
        ajouterListenerCompteur(donneesPanier);

        this.setTop(this.entete());
        this.setCenter(this.centre());
    }

    private BorderPane entete() {
        BorderPane top = new BorderPane();
        Label titre = new Label("Livre Express");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titre.setStyle("-fx-text-fill: white;");

        top.setLeft(titre);
        top.setRight(boutonRetour);
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

        btnSupprSel.setPrefWidth(180);
        btnViderPanier.setPrefWidth(180);
        HBox boutons = new HBox(10, btnSupprSel, btnViderPanier);

        gauche.getChildren().addAll(titrePanier, listeArticles, boutons);

        VBox droite = new VBox(10);
        droite.setPrefWidth(200);
        droite.setPadding(new Insets(0, 0, 0, 40));

        VBox recap = new VBox(10, lblNbArticles, lblPrixTotal);
        recap.setPadding(new Insets(10));
        recap.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        btnCommander.setPrefWidth(180);
        droite.getChildren().addAll(recap, btnCommander);

        HBox contenu = new HBox(40, gauche, droite);
        centre.setCenter(contenu);

        return centre;
    }

    private void mettreAJourCompteur(ObservableList<String> donneesPanier) {
        int taille = donneesPanier.size();
        if (taille <= 1) {
            lblNbArticles.setText(taille + " article");
        } else {
            lblNbArticles.setText(taille + " articles");
        }
    }

    private void ajouterListenerCompteur(ObservableList<String> donneesPanier) {
        donneesPanier.addListener(new javafx.collections.ListChangeListener<String>() {
            public void onChanged(Change<? extends String> changement) {
                mettreAJourCompteur(donneesPanier);
            }
        });
    }
}
