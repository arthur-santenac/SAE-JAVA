import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PageClient extends BorderPane {

    private Button boutonDeconnexion;
    private Button btnAjouterPanier;
    private ListView<Livre> catalogue;
    private ComboBox<String> filtreTheme;
    private ComboBox<String> filtreFiltre;
    private ComboBox<Integer> qte;

    public PageClient(Button boutonDeconnexion, Button btnAjouterPanier, ListView<Livre> catalogue, ComboBox<String> filtreTheme, ComboBox<String> filtreFiltre, ComboBox<Integer> qte) {

        super();
        this.boutonDeconnexion = boutonDeconnexion;
        this.btnAjouterPanier = btnAjouterPanier;
        this.catalogue = catalogue;
        this.filtreTheme = filtreTheme;
        this.filtreFiltre = filtreFiltre;
        this.qte = qte;
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
        TextField txtRecherche = new TextField();
        txtRecherche.setPromptText("Entrez le nom d'un livre");
        txtRecherche.setMaxWidth(Double.MAX_VALUE);

        TextArea resultatRecherche = new TextArea();
        resultatRecherche.setPrefHeight(400);

        Label info = new Label("Information pour plus trad a renter si on a besotting");
        VBox left = new VBox(10, txtRecherche, resultatRecherche, );    
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
        HBox quantite = new HBox(lblQte, qte);
        basCenter.setLeft(btnAjouterPanier);
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