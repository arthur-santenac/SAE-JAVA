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

    private Button btnLib;

    private Button btnVendeur;


    public PageAdmin(Button deconnexion, Button librairie, Button vendeur){
        this.deconnexion = deconnexion;
        this.btnLib = librairie;
        this.btnVendeur = vendeur;
        this.setTop(this.entete());
        this.setCenter(this.centre());
    }

    private BorderPane entete() {
        BorderPane entete = new BorderPane();
        Text titre = new Text();
        titre.setText("Livre Express - Administrateur");
        titre.setFont(Font.font("Arial", FontWeight.MEDIUM, 32));
        entete.setLeft(titre);
        entete.setRight(this.deconnexion);
        entete.setPadding(new Insets(10));
        entete.setBackground( new Background(new BackgroundFill(Color.GRAY,null,null)));
        return entete;
    }

    private GridPane centre(){
        GridPane root = new GridPane();
        Button btnStock = new Button("Stock");
        Button btnStats = new Button("Stats");
        root.add(this.btnLib, 0, 0, 4, 4);
        root.add(this.btnVendeur, 4, 0, 4, 4);
        root.add(btnStats, 0, 4, 4, 4);
        root.add(btnStock, 4, 4, 4, 4);
        root.setHgap(60);
        root.setVgap(40);
        this.btnLib.setPrefHeight(80);
        this.btnLib.setPrefWidth(200);
        this.btnVendeur.setPrefHeight(80);
        this.btnVendeur.setPrefWidth(200);
        btnStock.setPrefHeight(80);
        btnStock.setPrefWidth(200);
        btnStats.setPrefHeight(80);
        btnStats.setPrefWidth(200);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.BASELINE_CENTER);
        return root;
    }

}
