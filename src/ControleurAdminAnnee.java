import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ControleurAdminAnnee implements EventHandler<ActionEvent> {

    private ComboBox<String> choixAnnee;
    private ObservableList<PieChart.Data> pieChartCA;
    private AdminBD adminBD;

    public ControleurAdminAnnee(ComboBox<String> choixAnnee, ObservableList<PieChart.Data> pieChartCA, AdminBD adminBD) {
        this.choixAnnee = choixAnnee;
        this.pieChartCA = pieChartCA;
        this.adminBD = adminBD;
    }

    @Override
    public void handle(ActionEvent e) {
        Map<String, Double> caParTheme = new HashMap<>();
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
        
    }
}