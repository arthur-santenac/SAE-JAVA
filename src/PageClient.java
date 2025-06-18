import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PageClient extends BorderPane {

    private Button boutonDeconnexion;
    private Button btnAjouterPanierRecherche;
    private Button btnAjouterPanierCatalogue;
    private Button recherche;
    private ListView<Livre> listeRecherche;
    private ListView<Livre> catalogue;
    private ComboBox<String> filtreTheme;
    private ComboBox<String> filtreFiltre;
    private ComboBox<Integer> qteClient;
    private ComboBox<Integer> qteCatalogue;
    private TextField txtRecherche;

    public PageClient(Button boutonDeconnexion, Button recherche, Button btnAjouterPanierRecherche, Button btnAjouterPanierCatalogue, TextField txtRecherche, ListView<Livre> listeRecherche, ListView<Livre> catalogue, ComboBox<String> filtreTheme, ComboBox<String> filtreFiltre, ComboBox<Integer> qteClient, ComboBox<Integer> qteCatalogue) {

        super();
        this.boutonDeconnexion = boutonDeconnexion;
        this.btnAjouterPanierCatalogue = btnAjouterPanierCatalogue;
        this.catalogue = catalogue;
        this.recherche = recherche;
        this.filtreTheme = filtreTheme;
        this.listeRecherche = listeRecherche;
        this.filtreFiltre = filtreFiltre;
        this.qteCatalogue = qteCatalogue;
        this.txtRecherche = txtRecherche;
        this.qteClient = qteClient;
        this.btnAjouterPanierRecherche = btnAjouterPanierRecherche;
        this.setTop(this.top());
        this.setLeft(this.left());
        this.setCenter(this.center());
        this.setRight(this.right());

    }

    public BorderPane top() {
        Label titre = new Label("Livre Express");
        BorderPane top = new BorderPane();
        top.setLeft(titre);
        top.setRight(boutonDeconnexion);
        top.setPadding(new Insets(15));
        top.setStyle("-fx-background-color: #666;");
        titre.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
        return top;
    }

    public VBox left() {
        BorderPane teteLeft = new BorderPane();
        teteLeft.setLeft(txtRecherche);
        teteLeft.setRight(recherche);


        listeRecherche.setPrefHeight(400);
        listeRecherche.setPrefWidth(400);
        BorderPane basLeft = new BorderPane();
        Label lblQte = new Label("Quantité : ");
        lblQte.setStyle("-fx-font-size: 20px;");
        HBox quantite = new HBox(lblQte, qteClient);
        basLeft.setLeft(btnAjouterPanierRecherche);
        basLeft.setRight(quantite);
        
        VBox left = new VBox(10, teteLeft, listeRecherche, basLeft);    
        left.setPadding(new Insets(10));
        left.setFillWidth(true);
        return left;
    }

    public VBox center() {
        Label lblCatalogue = new Label("Catalogue");
        lblCatalogue.setStyle("-fx-font-size: 20px;");
        BorderPane teteCenter = new BorderPane();
        teteCenter.setLeft(lblCatalogue);
        teteCenter.setCenter(this.filtreTheme);
        teteCenter.setRight(this.filtreFiltre);
        teteCenter.setPadding(new Insets(10));
        BorderPane basCenter = new BorderPane();
        Label lblQte = new Label("Quantité : ");
        lblQte.setStyle("-fx-font-size: 20px;");
        HBox quantite = new HBox(lblQte, qteCatalogue);
        basCenter.setLeft(btnAjouterPanierCatalogue);
        basCenter.setRight(quantite);
        catalogue.setPrefHeight(600);
        VBox center = new VBox(10, teteCenter, catalogue, basCenter);
        center.setPadding(new Insets(10));
        center.setFillWidth(true);
        return center;
    }

    
    public VBox right() {
        Label lblSelection = new Label("Notre sélection pour vous");
        TextArea selection = new TextArea();
        selection.setPrefHeight(400);

        Button panier1 = new Button("panier");
        Button panier2 = new Button("panier");
        Button panier3 = new Button("panier");
        Button panier4 = new Button("panier");

        VBox boutonsPanier = new VBox(10, panier1, panier2, panier3, panier4);
        VBox right = new VBox(10, lblSelection, selection, boutonsPanier);
        right.setPadding(new Insets(10));
        right.setFillWidth(true);
        return right;
    }
}