import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class pageAdmin {

    private final Scene scene;

    private Button btnLibrairies = new Button("Gérer les Librairies");
    private Button btnVendeurs = new Button("Gérer les Vendeur");
    private Button btnStocks = new Button("Gérer les stocks");
    private Button btnStats = new Button("Afficher les statistiques");
    private Button btnLogout = new Button("Déconnexion");

    public pageAdmin(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPrefSize(800, 450);
        root.setStyle("-fx-background-color: #d9d9d9;");

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10));
        header.setSpacing(10);
        header.setStyle("-fx-background-color: #555555;");

        Label title = new Label("Livre Express - Administrateur");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18;");

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(title, spacer, btnLogout);
        root.setTop(header);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40, 60, 40, 60));
        grid.setHgap(120);
        grid.setVgap(60);
        grid.setAlignment(Pos.TOP_CENTER);

        for (Button b : new Button[] { btnLibrairies, btnVendeurs, btnStocks, btnStats }) {
            b.setMinSize(160, 40);
        }

        grid.add(btnLibrairies, 0, 0);
        grid.add(btnVendeurs, 1, 0);
        grid.add(btnStocks, 2, 0);
        grid.add(btnStats, 0, 1);
        root.setCenter(grid);

        this.scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        btnLibrairies.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Gérer les Librairies");
            }
        });

        btnVendeurs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Gérer les Vendeurs");
            }
        });

        btnStocks.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Gérer les Stocks");
            }
        });

        btnStats.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Afficher les Statistiques");
            }
        });

        btnLogout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Déconnexion");
                stage.close();
            }
        });
    }

    public Scene getScene() {
        return scene;
    }

    public Button getBtnLibrairies() {
        return btnLibrairies;
    }

    public Button getBtnVendeurs() {
        return btnVendeurs;
    }

    public Button getBtnStocks() {
        return btnStocks;
    }

    public Button getBtnStats() {
        return btnStats;
    }

    public Button getBtnLogout() {
        return btnLogout;
    }
}
