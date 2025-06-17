import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PageAdminLibrairie extends BorderPane{
    

    private Button retour;
    private AdminBD adminBD;
    private MagasinBD magasinBD;


    public PageAdminLibrairie(Button retour, AdminBD adminBD, MagasinBD magasinBD, ConnexionMySQL laConnexion){
        this.retour = retour;
        this.adminBD = new AdminBD(laConnexion);
        this.magasinBD = new MagasinBD(laConnexion);
        this.setTop(this.entete());
        this.setCenter(this.centre());
    }

    private BorderPane entete() {
        BorderPane entete = new BorderPane();
        Text titre = new Text();
        titre.setText("Livre Express - Administrateur");
        titre.setFont(Font.font("Arial", FontWeight.MEDIUM, 32));
        entete.setLeft(titre);
        entete.setRight(this.retour);
        entete.setPadding(new Insets(10));
        entete.setBackground( new Background(new BackgroundFill(Color.GRAY,null,null)));
        return entete;
    }

    private BorderPane centre(){
        BorderPane root = new BorderPane();
        VBox marge = new VBox(10);
        Button btnAjoutV = new Button("Ajouter un vendeur");
        Button btnSuppV = new Button("Supprimer un vendeur");
        marge.getChildren().addAll(btnAjoutV, btnSuppV);
        btnAjoutV.setPrefHeight(60);
        btnAjoutV.setPrefWidth(200);
        btnSuppV.setPrefHeight(60);
        btnSuppV.setPrefWidth(200);
        VBox centre = new VBox(10);
        Label titreV = new Label("Vendeur");
        Label textListe = new Label("Liste de librairies");
        VBox listeL = new VBox(10);
        try{
            List<Magasin> listeLib = this.magasinBD.listeDesMagasins();
            for(Magasin m : listeLib){
                Label lib = new Label(m.toString());
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
        root.setRight(marge);
        return root;
    }

    
}
