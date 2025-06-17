import javafx.geometry.Insets;
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
        root.add(btnLib, 0, 0, 2, 1);
        root.add(btnVendeur, 2, 1, 2, 1);
        root.add(btnStock, 4, 0, 2, 1);
        root.add(btnStats, 6, 0, 2, 1);
        return root;
    }

}
