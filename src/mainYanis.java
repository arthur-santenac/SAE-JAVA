import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class mainYanis extends Application {

    @Override
    public void start(Stage primaryStage) {
        ObservableList<String> donneesPanier = FXCollections.observableArrayList();
        Button btnPrecedent = new Button("précédent");
        ListView<String> listeArticles = new ListView<>();
        Label lblNbArticles = new Label();
        Label lblPrixTotal = new Label("prix total : 0 €");
        Button btnCommander = new Button("Commander");
        Button btnSupprSel = new Button("supprimer un article");
        Button btnViderPanier = new Button("supprimer tout les article");
        PagePanierClient root = new PagePanierClient(
                btnPrecedent,
                listeArticles,
                lblNbArticles,
                lblPrixTotal,
                btnCommander,
                btnSupprSel,
                btnViderPanier,
                donneesPanier
        );
        Scene scene = new Scene(root, 1050, 650);
        primaryStage.setTitle("Livre Express - Panier");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

