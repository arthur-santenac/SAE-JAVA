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

public class PageAdminVendeur extends BorderPane{
    

    private LivreExpress app;
    private ClientBD clientBD;



    public PageAdminVendeur(LivreExpress app){
        this.app =app;
        this.clientBD = new ClientBD(this.app.getLaConnexion());
        this.setTop(this.entete());
        this.setCenter(this.centre());
    }

    private BorderPane entete() {
        BorderPane entete = new BorderPane();
        Label titre = new Label("Livre Express - Administrateur");
        titre.setStyle("-fx-text-fill: white;");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        entete.setLeft(titre);
        entete.setRight(this.app.getBtnRetourAdmin());
        entete.setPadding(new Insets(10));
        entete.setBackground( new Background(new BackgroundFill(Color.GRAY,null,null)));
        return entete;
    }

    private BorderPane centre(){
        BorderPane root = new BorderPane();
        VBox marge = new VBox(10);
        marge.setPadding(new Insets(0, 0, 0, 20));
        marge.getChildren().addAll(this.app.getBtnAddVendeur(), this.app.getBtnSuppVendeur());
        this.app.getBtnAddVendeur().setPrefHeight(60);
        this.app.getBtnAddVendeur().setPrefWidth(200);
        this.app.getBtnSuppVendeur().setPrefHeight(60);
        this.app.getBtnSuppVendeur().setPrefWidth(200);
        VBox centre = new VBox(10);
        centre.setPadding(new Insets(0, 20, 0, 0));
        Label titreV = new Label("Vendeur");
        titreV.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        Label textListe = new Label("Liste de vendeurs");
        textListe.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        VBox listeL = new VBox(10);
        try{
            List<Client> listeVendeurs = this.clientBD.listeDesVendeurs();
            for(Client c : listeVendeurs){
                Label lib = new Label(c.toString());
                lib.setPadding(new Insets(10));
                listeL.getChildren().add(lib);
            }
        }catch(SQLException e){
            System.out.println("Erreur d'affichage de la liste des vendeurs");
        }
        listeL.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-style: solid;");
        centre.getChildren().addAll(titreV,textListe, listeL);
        root.setPadding(new Insets(20));
        root.setCenter(centre);
        root.setRight(marge);
        return root;
    }

    
}
