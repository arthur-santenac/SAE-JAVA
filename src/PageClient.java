import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PageClient extends BorderPane {

    private LivreExpress appli;

    public PageClient(LivreExpress appli) {

        super();
        this.appli = appli;
        this.setTop(this.top());
        this.setLeft(this.left());
        this.setCenter(this.center());
        this.setRight(this.right());
    }

    public BorderPane top() {
        Label titre = new Label("Livre Express");
        BorderPane top = new BorderPane();
        top.setLeft(titre);
        top.setRight(appli.getBoutonDeconnexion());
        top.setPadding(new Insets(15));
        top.setStyle("-fx-background-color: #666;");
        titre.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
        return top;
    }

    public VBox left() {
        BorderPane teteLeft = new BorderPane();
        teteLeft.setLeft(this.appli.getTxtRecherche());
        teteLeft.setRight(this.appli.getRecherche());

        this.appli.getListeRecherche().setPrefHeight(400);
        this.appli.getListeRecherche().setPrefWidth(420);
        BorderPane basLeft = new BorderPane();
        Label lblQte = new Label("Quantité : ");
        lblQte.setStyle("-fx-font-size: 20px;");
        HBox quantite = new HBox(lblQte, this.appli.getQteClient());
        basLeft.setLeft(this.appli.getAjouteRecherche());
        basLeft.setRight(quantite);
        
        VBox left = new VBox(10, teteLeft, this.appli.getListeRecherche(), basLeft);    
        left.setPadding(new Insets(10));
        left.setFillWidth(true);
        return left;
    }

    public VBox center() {
        Label lblCatalogue = new Label("Catalogue");
        lblCatalogue.setStyle("-fx-font-size: 20px;");
        BorderPane teteCenter = new BorderPane();
        teteCenter.setLeft(lblCatalogue);
        teteCenter.setCenter(this.appli.getFiltreTheme());
        teteCenter.setRight(this.appli.getFiltreFiltre());
        teteCenter.setPadding(new Insets(10));
        BorderPane basCenter = new BorderPane();
        Label lblQte = new Label("Quantité : ");
        lblQte.setStyle("-fx-font-size: 20px;");
        HBox quantite = new HBox(lblQte, this.appli.getQteCatalogue());
        basCenter.setLeft(this.appli.getAjouteCatalogue());
        basCenter.setRight(quantite);
        this.appli.getCatalogue().setPrefHeight(600);
        VBox center = new VBox(10, teteCenter, this.appli.getCatalogue(), basCenter);
        center.setPadding(new Insets(10));
        center.setFillWidth(true);
        return center;
    }

    
    public VBox right() {
        Label lblSelection = new Label("Notre sélection pour vous");
        this.appli.getRecommande().setPrefHeight(400);
        this.appli.getRecommande().setPrefWidth(420);
        Label lblQte = new Label("Quantité : ");
        lblQte.setStyle("-fx-font-size: 20px;");
        HBox quantite = new HBox(lblQte, this.appli.getQteRecom());
        BorderPane commande = new BorderPane();
        commande.setLeft(this.appli.getAjouteRecom());
        commande.setRight(quantite);

        Button boutonPanier = this.appli.getBoutonPanier();
        boutonPanier.setMaxWidth(Double.MAX_VALUE);
        boutonPanier.setPrefHeight(150);
        BorderPane basRight = new BorderPane();
        basRight.setBottom(boutonPanier);
        BorderPane.setMargin(boutonPanier, new Insets(10, 0, 0, 0));

        VBox right = new VBox(10, lblSelection, this.appli.getRecommande(), commande, basRight);
        right.setPadding(new Insets(10));
        right.setFillWidth(true);

        VBox.setVgrow(this.appli.getRecommande(), Priority.ALWAYS);
        VBox.setVgrow(basRight, Priority.ALWAYS);

        return right;
    }
}