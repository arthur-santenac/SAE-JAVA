import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

    private VBox centre(){
        VBox root = new VBox(10);
        HBox titre = new HBox(10);
        Label dash = new Label("Dashboard");
        dash.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        dash.setStyle("-fx-text-fill: white;");
        titre.getChildren().add(dash);
        GridPane centre = new GridPane();
        VBox stats1 = new VBox(10);
        Label titreBar = new Label("Nombre de livres vendus par magasin");
        titreBar.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titreBar.setPadding(new Insets(10));
        BarChart<String, Number> barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        try{
            List<XYChart.Series<String, Number>> seriesList = this.magasinBD.getVentesParAnnee();
            barChart.getData().addAll(seriesList);
            stats1.getChildren().addAll(titreBar, barChart);
        }catch(SQLException e){
            System.out.println("Erreur dans le graphique 1");
        }

        VBox stats2 = new VBox(10);
        Label titrePie = new Label("Chiffre d'affaire 2024 par thèmes");

        PieChart pieChart = new PieChart();
        
        titrePie.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titrePie.setPadding(new Insets(10));
        stats2.getChildren().addAll(titrePie, pieChart);
        

        stats1.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: lightgray; -fx-border-radius: 10;");
        stats2.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: lightgray; -fx-border-radius: 10;");
        centre.add(stats1, 0, 0, 2, 3);
        centre.add(stats2, 2, 0, 2, 3);
        centre.setHgap(20);
        centre.setVgap(20);
        root.getChildren().addAll(titre, centre);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color:rgb(10, 10, 10)");
        return root;
    }
    
}
