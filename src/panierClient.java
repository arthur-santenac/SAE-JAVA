// panierClient.java
import javafx.application.Application;
import javafx.beans.binding.*;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.scene.control.Button;

public class panierClient extends Application {

    @Override
    public void start(Stage stage) {
        Button boutonDeconnexion = new Button("Déconnexion");
        PageClient pageClientRoot = new PageClient(boutonDeconnexion);
        Scene scenePageClient = new Scene(pageClientRoot, 1024, 640);
        HBox barreHaut = new HBox();
        barreHaut.setAlignment(Pos.CENTER_LEFT);
        barreHaut.setPadding(new Insets(8));
        barreHaut.setSpacing(10);
        barreHaut.setStyle("-fx-background-color:#58595B;");

        Label titreLabel = new Label("Livre Express");
        titreLabel.setFont(Font.font("Helvetica Neue", 24));
        titreLabel.setStyle("-fx-text-fill:white;");

        Region espacement = new Region();
        HBox.setHgrow(espacement, Priority.ALWAYS);

        Button boutonRetour = new Button("précédent");
        boutonRetour.setStyle("-fx-background-radius:6;");
        boutonRetour.setOnAction(e -> stage.setScene(scenePageClient));

        barreHaut.getChildren().addAll(titreLabel, espacement, boutonRetour);
        ObservableList<Livre> articlesPanier = FXCollections.observableArrayList();

        ListView<Livre> listePanier = new ListView<>(articlesPanier);
        listePanier.setPrefSize(350, 330);
        listePanier.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Livre livre, boolean vide) {
                super.updateItem(livre, vide);
                setText(vide || livre == null ? "" : livre.toString());
            }
        });

        Label etiquettePanier = new Label("Votre panier");

        Button boutonSupprimerUn = new Button("Supprimer un article");
        boutonSupprimerUn.setOnAction(e -> {
            Livre selection = listePanier.getSelectionModel().getSelectedItem();
            if (selection != null) {
                articlesPanier.remove(selection);
            }
        });

        Button boutonSupprimerTout = new Button("Supprimer tous les articles");
        boutonSupprimerTout.setOnAction(e -> articlesPanier.clear());

        HBox boutonsPanier = new HBox(10, boutonSupprimerUn, boutonSupprimerTout);
        VBox boitePanier = new VBox(8, etiquettePanier, listePanier, boutonsPanier);
        Label etiquetteNombre = new Label();
        IntegerBinding nombreLivres = Bindings.size(articlesPanier);
        etiquetteNombre.textProperty().bind(Bindings.createStringBinding(() -> {
            int n = nombreLivres.get();
            return n + (n == 1 ? " livre" : " livres");
        }, nombreLivres));

        Label etiquetteTotal = new Label();
        DoubleBinding prixTotal = Bindings.createDoubleBinding(
                () -> articlesPanier.stream().mapToDouble(Livre::getPrix).sum(),
                articlesPanier);
        etiquetteTotal.textProperty().bind(prixTotal.asString("prix total : %.2f €"));

        Button boutonCommander = new Button("Commander");
        boutonCommander.setMaxWidth(Double.MAX_VALUE);

        VBox boiteRecap = new VBox(20, etiquetteNombre, etiquetteTotal, boutonCommander);
        boiteRecap.setAlignment(Pos.TOP_CENTER);
        boiteRecap.setPadding(new Insets(15));
        boiteRecap.setStyle("-fx-border-color:lightgray; -fx-border-width:2; "
                + "-fx-background-color:rgba(0,0,0,0.02);");
        HBox boiteCentre = new HBox(40, boitePanier, boiteRecap);
        boiteCentre.setAlignment(Pos.TOP_CENTER);
        boiteCentre.setPadding(new Insets(30));
        boiteCentre.setFillHeight(false);

        BorderPane racine = new BorderPane(boiteCentre);
        racine.setTop(barreHaut);

        Scene scenePanier = new Scene(racine, 1024, 640);
        stage.setTitle("Livre Express - Panier");
        stage.setScene(scenePanier);
        stage.show();
    }
}
