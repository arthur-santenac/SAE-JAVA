import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PageAdmin extends BorderPane{
    

    private Button deconnexion;


    public PageAdmin(Button deconnexion){
        this.deconnexion = deconnexion;
        this.setTop(this.entete());
        this.setCenter(this.centre());
    }

    private BorderPane entete() {
        BorderPane entete = new BorderPane();
        Text titre = new Text();
        titre.setText("Livre Express - Administrateur");
        titre.setFont(Font.font("Arial", FontWeight.MEDIUM, 32));
        entete.setLeft(titre);
        entete.setPadding(new Insets(10));
        entete.setBackground( new Background(new BackgroundFill(Color.GRAY,null,null)));
        return entete;
    }

    private GridPane centre(){
        GridPane root = new GridPane();
        Button btnLib = new Button("Librairie");
        Button btnVendeur = new Button("Vendeur");
        Button btnStock = new Button("Stock");
        Button btnStats = new Button("Stats");
        root.add(btnLib, 0, 0, 3, 3);
        root.add(btnVendeur, 3, 0, 3, 3);
        root.add(btnStock, 6, 0, 3, 3);
        root.add(btnStats, 0, 4, 3, 3);
        root.setHgap(60);
        root.setVgap(40);
        btnLib.setPrefHeight(60);
        btnLib.setPrefWidth(200);
        btnVendeur.setPrefHeight(60);
        btnVendeur.setPrefWidth(200);
        btnVendeur.setPrefHeight(60);
        btnVendeur.setPrefWidth(200);
        btnStock.setPrefHeight(60);
        btnStock.setPrefWidth(200);
        btnStats.setPrefHeight(60);
        btnStats.setPrefWidth(200);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.BASELINE_CENTER);
        return root;
    }

}
