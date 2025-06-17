import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class VendeurAdminApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label title = new Label("Livre Express - Administrateur");
        title.setStyle("-fx-font-size: 22px; -fx-text-fill: white;");

        Button logoutBtn = new Button("Déconnexion");
        logoutBtn.setFocusTraversable(false);

        Region spacerHeader = new Region();
        HBox.setHgrow(spacerHeader, Priority.ALWAYS);

        HBox header = new HBox(10, title, spacerHeader, logoutBtn);
        header.setPadding(new Insets(10));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #5b5b5f;");

        Label sectionTitle = new Label("Vendeur");
        sectionTitle.setStyle("-fx-font-size: 28px;");

        Label listLbl = new Label("Liste des vendeurs");
        ListView<String> vendeurList = new ListView<>();
        VBox listBox = new VBox(5, listLbl, vendeurList);
        listBox.setPadding(new Insets(0, 0, 0, 20));
        HBox.setHgrow(listBox, Priority.NEVER);

        Button addBtn = new Button("Ajouter un vendeur");
        addBtn.setMaxWidth(Double.MAX_VALUE);
        Button delBtn = new Button("Supprimer un vendeur");
        delBtn.setMaxWidth(Double.MAX_VALUE);

        VBox btnBox = new VBox(20, addBtn, delBtn);
        btnBox.setAlignment(Pos.TOP_CENTER);
        btnBox.setPadding(new Insets(20));
        btnBox.setPrefWidth(200);

        Region spacerCenter = new Region();
        HBox.setHgrow(spacerCenter, Priority.ALWAYS);

        HBox center = new HBox(20, listBox, spacerCenter, btnBox);
        center.setPadding(new Insets(20));
        VBox content = new VBox(15, sectionTitle, center);

        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(content);
        root.setStyle("-fx-background-color: #e6e7ea;");

        Scene scene = new Scene(root, 1050, 560);

        vendeurList.prefHeightProperty().bind(scene.heightProperty().multiply(0.5));
        listBox.prefWidthProperty().bind(scene.widthProperty().multiply(0.4));
        listBox.maxWidthProperty().bind(scene.widthProperty().multiply(0.4));

        primaryStage.setScene(scene);
        primaryStage.setTitle("Livre Express - Administrateur");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
