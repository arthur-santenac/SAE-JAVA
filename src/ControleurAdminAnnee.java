import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import java.sql.SQLException;
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
        String anneeChoisie = choixAnnee.getValue();
        if (anneeChoisie != null) {
            try {
                int annee = Integer.parseInt(anneeChoisie);
                Map<String, Double> caParTheme = adminBD.caParTheme(annee);
                pieChartCA.clear(); // Clear old data
                for (Map.Entry<String, Double> entry : caParTheme.entrySet()) {
                    double value = entry.getValue();
                    if (value > 0) {
                        pieChartCA.add(new PieChart.Data(entry.getKey(), value));
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Erreur SQL lors du changement d'année : " + anneeChoisie);
            }
        }
    }
}