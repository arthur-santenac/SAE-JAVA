import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private AdminBD adminBD;



    public PageAdminStats(Button retour,  ConnexionMySQL laConnexion){
        this.retour = retour;
        this.magasinBD = new MagasinBD(laConnexion);
        this.adminBD = new AdminBD(laConnexion);
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
        dash.setAlignment(Pos.BASELINE_CENTER);
        dash.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        dash.setStyle("-fx-text-fill: white;");
        titre.getChildren().add(dash);
        GridPane centre = new GridPane();
        VBox stats1 = new VBox(10);
        Label titreBar = new Label("Nombre de livres vendus par magasin");
        titreBar.setAlignment(Pos.BASELINE_CENTER);
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
        Label titrePie = new Label("Chiffre d'affaire par thèmes \n sur une année donnée");
        titrePie.setAlignment(Pos.BASELINE_CENTER);
        ComboBox<String> choixAnnee = new ComboBox<String>();
        choixAnnee.getItems().addAll("2020", "2021", "2022", "2023", "2024", "2025");
        choixAnnee.setValue("2025");
        Map<String, Double> caParTheme = new HashMap<>();
        ObservableList<PieChart.Data> pieChartCA = FXCollections.observableArrayList();
        PieChart pieChart = new PieChart(pieChartCA);
        choixAnnee.setOnAction(new ControleurAdminAnnee(choixAnnee, pieChartCA, this.adminBD));
        String anneeChoisie = choixAnnee.getValue();
        if (anneeChoisie != null) {
            try {
                int annee = Integer.parseInt(anneeChoisie);
                caParTheme = adminBD.caParTheme(annee);
                pieChartCA.clear();
                for (Map.Entry<String, Double> entry : caParTheme.entrySet()) {
                    String theme = entry.getKey();
                    double value = entry.getValue();
                    if (value > 0) { 
                        pieChartCA.add(new PieChart.Data(theme, value));
                    }
                }
            } catch (SQLException e) {
                System.out.println("Erreur à la récupération du CA pour l'année " + anneeChoisie);
            }
        }   
        titrePie.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titrePie.setPadding(new Insets(10));
        stats2.getChildren().addAll(titrePie, choixAnnee, pieChart);
        

        stats1.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: lightgray; -fx-border-radius: 10;");
        stats2.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: lightgray; -fx-border-radius: 10;");
        centre.add(stats1, 0, 0, 2, 3);
        centre.add(stats2, 2, 0, 2, 3);
        centre.setHgap(20);
        centre.setVgap(20);
        root.getChildren().addAll(titre, centre);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color:rgb(63, 63, 63)");
        return root;
    }
    
}
