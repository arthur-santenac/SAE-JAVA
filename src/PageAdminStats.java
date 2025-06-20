import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;

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
        Label titreBar = new Label("Nombre de livres vendus \n par magasin");
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
        ComboBox<String> choixAnnee = new ComboBox<String>();
        choixAnnee.getItems().addAll("2020", "2021", "2022", "2023", "2024", "2025");
        choixAnnee.setValue("2025");
        Map<String, Double> caParTheme = new HashMap<>();
        ObservableList<PieChart.Data> pieChartCA = FXCollections.observableArrayList();
        PieChart pieChart = new PieChart(pieChartCA);
        pieChart.setPrefSize(250, 250);
        pieChart.setMinSize(250, 250);
        pieChart.setMaxSize(250, 250);
        pieChart.setLabelsVisible(false);
        choixAnnee.setOnAction(new ControleurAdminAnnee(choixAnnee, pieChartCA, this.adminBD));
        String anneeChoisie = choixAnnee.getValue();
        if (anneeChoisie != null) {
            try {
                int annee = Integer.parseInt(anneeChoisie);
                caParTheme = this.adminBD.caParTheme(annee);
                pieChartCA.clear();
                double total = caParTheme.values().stream().mapToDouble(Double::doubleValue).sum();
                for (Map.Entry<String, Double> entry : caParTheme.entrySet()) {
                    String theme = entry.getKey();
                    double value = entry.getValue();
                    if (value > 0 && total > 0) {
                        double pourcentage = (value / total) * 100;
                        double pourcentageArrondi = Math.round(pourcentage * 10) / 10.0;  
                        String label = theme + " (" + pourcentageArrondi + "%)";
                        pieChartCA.add(new PieChart.Data(label, value));
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Erreur SQL lors du changement d'année : " + anneeChoisie);
            }
        }   
        titrePie.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titrePie.setAlignment(Pos.BASELINE_CENTER);
        titrePie.setPadding(new Insets(10));
        stats2.getChildren().addAll(titrePie, choixAnnee, pieChart);
        stats2.setAlignment(Pos.CENTER);
        
        VBox stats3 = new VBox(10);
        Label titreTopAuteur = new Label("Auteur ayant le plus vendu par année \n (hors 2025)");
        titreTopAuteur.setAlignment(Pos.BASELINE_CENTER);
        titreTopAuteur.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titreTopAuteur.setPadding(new Insets(10));
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Année");
        xAxis.setCategories(FXCollections.observableArrayList("2020", "2021", "2022", "2023", "2024"));
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Nombre de livres vendus");
        BarChart<String, Number> barChartPalmares = new BarChart<>(xAxis, yAxis);
        barChartPalmares.setLegendVisible(false); 
        XYChart.Series<String, Number> serieUnique = new XYChart.Series<>();
        Map<String, String> auteurParAnnee = new LinkedHashMap<>();
        Map<String, Color> couleurParAuteur = new LinkedHashMap<>();
        try {
            List<String> palmares = this.adminBD.palmares();
            for (String ligne : palmares) {
                String[] parties = ligne.split(" ");
                int ventes = Integer.parseInt(parties[parties.length - 1]);
                String annee = parties[parties.length - 2];
                String nomAuteur = ligne.substring(0, ligne.lastIndexOf(" ", ligne.lastIndexOf(" ") - 1));
                auteurParAnnee.put(annee, nomAuteur);
                XYChart.Data<String, Number> data = new XYChart.Data<>(annee, ventes);
                serieUnique.getData().add(data);
                couleurParAuteur.putIfAbsent(nomAuteur, Color.color(Math.random(), Math.random(), Math.random()));
                data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                    if (newNode != null) {
                        Color couleur = couleurParAuteur.get(nomAuteur);
                        String style = String.format("-fx-bar-fill: rgb(%d,%d,%d);",
                                (int) (couleur.getRed() * 255),
                                (int) (couleur.getGreen() * 255),
                                (int) (couleur.getBlue() * 255));
                        newNode.setStyle(style);
                        Tooltip.install(newNode, new Tooltip(nomAuteur + " : " + ventes + " ventes"));
                    }
                });
            }
            barChartPalmares.getData().add(serieUnique);
            HBox legende = new HBox(10);
            legende.setPadding(new Insets(10));
            for (Map.Entry<String, Color> entry : couleurParAuteur.entrySet()) {
                String nomAuteur = entry.getKey();
                Color color = entry.getValue();
                Rectangle rect = new Rectangle(15, 15, color);
                Label label = new Label(nomAuteur);
                HBox item = new HBox(5, rect, label);
                legende.getChildren().add(item);
            }
            stats3.getChildren().addAll(titreTopAuteur, barChartPalmares, legende);
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement du palmarès");
        }


        VBox stats4 = new VBox(10);
        Label titreTopEditeur = new Label("10 éditeurs ayant le plus d'auteurs");
        titreTopEditeur.setAlignment(Pos.BASELINE_CENTER);
        titreTopEditeur.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titreTopEditeur.setPadding(new Insets(10));
        CategoryAxis x = new CategoryAxis();
        x.setLabel("Éditeur");
        NumberAxis y = new NumberAxis();
        y.setLabel("Nombre d'auteurs distincts");
        BarChart<String, Number> barChartEdit = new BarChart<>(x, y);
        barChartEdit.setLegendVisible(false);
        XYChart.Series<String, Number> serieEdit = new XYChart.Series<>();
        Map<String, Color> couleurParEditeur = new LinkedHashMap<>();
        try {
            List<String> meilleurs = this.adminBD.meilleursEdit(); 
            for (String ligne : meilleurs) {
                String[] parties = ligne.split(" : ");
                String nomEditeur = parties[0].trim();
                int nbAuteurs = Integer.parseInt(parties[1].trim());
                XYChart.Data<String, Number> data = new XYChart.Data<>(nomEditeur, nbAuteurs);
                serieEdit.getData().add(data);
                Color couleur = Color.color(Math.random(), Math.random(), Math.random());
                couleurParEditeur.put(nomEditeur, couleur);
                data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                    if (newNode != null) {
                        Color c = couleurParEditeur.get(nomEditeur);
                        String style = String.format("-fx-bar-fill: rgb(%d,%d,%d);",
                                (int) (c.getRed() * 255),
                                (int) (c.getGreen() * 255),
                                (int) (c.getBlue() * 255));
                        newNode.setStyle(style);
                        Tooltip.install(newNode, new Tooltip(nomEditeur + " : " + nbAuteurs + " auteurs"));
                    }
                });
            }
            barChartEdit.getData().add(serieEdit);
            stats4.getChildren().addAll(titreTopEditeur, barChartEdit);
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement des meilleurs éditeurs");
        }
        stats1.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: lightgray; -fx-border-radius: 10;");
        stats2.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: lightgray; -fx-border-radius: 10;");
        stats3.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: lightgray; -fx-border-radius: 10;");
        stats4.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: lightgray; -fx-border-radius: 10;");
        centre.add(stats1, 0, 0, 5, 4);
        centre.add(stats2, 5, 0, 7, 4);
        centre.add(stats3, 0, 5, 4, 3);
        centre.add(stats4, 4, 5, 8, 3);
        centre.setHgap(20);
        centre.setVgap(20);
        root.getChildren().addAll(titre, centre);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color:rgb(63, 63, 63)");
        return root;
    }
    
}
