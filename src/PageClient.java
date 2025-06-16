import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PageClient extends BorderPane {

    public PageClient() {

        super();

        this.setTop(this.top());
        this.setLeft(this.left());
        this.setCenter(this.center());
        this.setRight(this.right());

    }

    public BorderPane top() {
        Label titre = new Label("Livre Express");
        Button btnDeconnexion = new Button("deconnexion");
        BorderPane top = new BorderPane();
        top.setLeft(titre);
        top.setRight(btnDeconnexion);
        top.setPadding(new Insets(15));
        top.setStyle("-fx-background-color: #666;");
        titre.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
        btnDeconnexion.setStyle("-fx-background-color: #ddd;");
        return top;
    }

    public VBox left() {
        TextField txtRecherche = new TextField();
        txtRecherche.setPromptText("Entrez le nom d'un livre");
        txtRecherche.setMaxWidth(Double.MAX_VALUE);

        TextArea resultatRecherche = new TextArea();
        resultatRecherche.setPrefHeight(400);

        Label info = new Label("Information pour plus trad a renter si on a besotting");
        VBox left = new VBox(10, txtRecherche, resultatRecherche, info);
        left.setPadding(new Insets(10));
        return left;
    }

    public VBox center() {
        Label lblCatalogue = new Label("Catalogue");
        TextArea catalogue = new TextArea();
        catalogue.setPrefHeight(600);
        VBox center = new VBox(10, lblCatalogue, catalogue);
        center.setPadding(new Insets(10));
        return center;
    }
    
    public VBox right() {
        Label lblSelection = new Label("Notre sélection pour vous");
        TextArea selection = new TextArea();
        selection.setPrefHeight(400);

        Button panier1 = new Button("panier");
        Button panier2 = new Button("panier");
        Button panier3 = new Button("panier");
        Button panier4 = new Button("panier");

        VBox boutonsPanier = new VBox(10, panier1, panier2, panier3, panier4);
        VBox right = new VBox(10, lblSelection, selection, boutonsPanier);
        right.setPadding(new Insets(10));
        return right;
    }
}

