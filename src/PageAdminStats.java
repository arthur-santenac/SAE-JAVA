import java.sql.SQLException;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PageAdminStats extends BorderPane{
    

    private Button retour;
    private MagasinBD magasinBD;



    public PageAdminStats(Button retour,  ConnexionMySQL laConnexion){
        this.retour = retour;
        this.magasinBD = new MagasinBD(laConnexion);
        this.setTop(this.entete());
        this.setCenter(this.centre());
    }

    private BorderPane entete() {
        BorderPane entete = new BorderPane();
        Label titre = new Label("Livre Express - Administrateur");
        titre.setStyle("-fx-text-fill: white;");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        entete.setLeft(titre);
        entete.setRight(this.retour);
        entete.setPadding(new Insets(10));
        entete.setBackground( new Background(new BackgroundFill(Color.GRAY,null,null)));
        return entete;
    }

    private VBox centre(){
        VBox root = new VBox(10);
        HBox titre = new HBox(10);
        Label dash = new Label("Dashboard");
        titre.getChildren().add(dash);
        
        GridPane centre = new GridPane();
        VBox stats1 = new VBox(10);
        Label txt1 = new Label("titre1");
        txt1.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        Label txt2 = new Label("titre2");

        stats1.getChildren().add(txt1);
        centre.add(stats1, 0, 0, 2, 3);
        centre.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #cccccc; -fx-border-width: 1;");
        root.getChildren().addAll(titre, centre);
        root.setPadding(new Insets(20));
        return root;
    }
    
}
