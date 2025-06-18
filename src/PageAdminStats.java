import java.sql.SQLException;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
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

    private BorderPane centre(){
        BorderPane root = new BorderPane();
        VBox centre = new VBox(10);
        centre.setPadding(new Insets(0, 20, 0, 0));
        Label titreV = new Label("Stats");
        titreV.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        Label textListe = new Label("Liste de librairies");
        textListe.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        VBox listeL = new VBox(10);
        try{
            List<Magasin> listeLib = this.magasinBD.listeDesMagasins();
            for(Magasin m : listeLib){
                Label lib = new Label(m.getIdMag()+" - "+ m.toString());
                lib.setPadding(new Insets(10));
                listeL.getChildren().add(lib);
            }
        }catch(SQLException e){
            System.out.println("Erreur d'affichage de la liste des librairies");
        }
        listeL.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-style: solid;");
        centre.getChildren().addAll(titreV,textListe, listeL);
        root.setPadding(new Insets(20));
        root.setCenter(centre);
        return root;
    }
    
}
