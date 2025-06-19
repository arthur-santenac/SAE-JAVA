import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    

    private LivreExpress app;


    public PageAdmin(LivreExpress app){
        this.app = app;
        this.setTop(this.entete());
        this.setCenter(this.centre());
    }

    private BorderPane entete() {
        BorderPane entete = new BorderPane();
        Label titre = new Label("Livre Express - Administrateur");
        titre.setStyle("-fx-text-fill: white;");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        entete.setLeft(titre);
        entete.setRight(this.app.getBoutonDeconnexion());
        entete.setPadding(new Insets(10));
        entete.setBackground( new Background(new BackgroundFill(Color.GRAY,null,null)));
        return entete;
    }

    private GridPane centre(){
        GridPane root = new GridPane();
        Button btnStock = new Button("Stock");
        root.add(this.app.getBtnLib(), 0, 0, 4, 4);
        root.add(this.app.getBtnVend(), 4, 0, 4, 4);
        root.add(this.app.getBtnStats(), 0, 4, 4, 4);
        root.add(btnStock, 4, 4, 4, 4);
        root.setHgap(20);
        root.setVgap(20);
        this.app.getBtnLib().setPrefHeight(80);
        this.app.getBtnLib().setPrefWidth(200);
        this.app.getBtnVend().setPrefHeight(80);
        this.app.getBtnVend().setPrefWidth(200);
        btnStock.setPrefHeight(80);
        btnStock.setPrefWidth(200);
        this.app.getBtnStats().setPrefHeight(80);
        this.app.getBtnStats().setPrefWidth(200);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);
        return root;
    }

}
