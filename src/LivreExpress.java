import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LivreExpress extends Application {

    @Override
    public void start(Stage primaryStage) {
        // --- TOP ---
        Label titre = new Label("Livre Express");
        Button btnDeconnexion = new Button("deconnexion");

        HBox topBar = new HBox(20, titre, btnDeconnexion);
        topBar.setStyle("-fx-padding: 10; -fx-alignment: center_left; -fx-background-color: #666; -fx-spacing: 20;");
        titre.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
        btnDeconnexion.setStyle("-fx-background-color: #ddd;");

        // --- LEFT ---
        TextField txtRecherche = new TextField();
        txtRecherche.setPromptText("Entrez le nom d'un livre");
        txtRecherche.setMaxWidth(Double.MAX_VALUE);

        TextArea resultatRecherche = new TextArea();
        resultatRecherche.setPrefHeight(200);

        Label info = new Label("Information pour plus trad a renter si on a besotting");

        VBox leftPane = new VBox(10, txtRecherche, resultatRecherche, info);
        leftPane.setStyle("-fx-padding: 10;");

        // --- CENTER ---
        Label lblCatalogue = new Label("Catalogue");
        TextArea catalogue = new TextArea();
        catalogue.setPrefHeight(400);

        VBox centerPane = new VBox(10, lblCatalogue, catalogue);
        centerPane.setStyle("-fx-padding: 10;");

        // --- RIGHT ---
        Label lblSelection = new Label("Notre sélection pour vous");
        TextArea selection = new TextArea();
        selection.setPrefHeight(200);

        Button panier1 = new Button("panier");
        Button panier2 = new Button("panier");
        Button panier3 = new Button("panier");
        Button panier4 = new Button("panier");

        // Empile les boutons
        VBox boutonsPanier = new VBox(10, panier1, panier2, panier3, panier4);

        VBox rightPane = new VBox(10, lblSelection, selection, boutonsPanier);
        rightPane.setStyle("-fx-padding: 10;");

        // --- ROOT ---
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setLeft(leftPane);
        root.setCenter(centerPane);
        root.setRight(rightPane);

        Scene scene = new Scene(root, 1000, 600);

        primaryStage.setTitle("Livre Express");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
