import javafx.event.EventHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import org.w3c.dom.events.Event;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

public class ControleurVendeurMajQte implements EventHandler<ActionEvent> {

    private int idMag;
    private ConnexionMySQL laConnexion;
    private VendeurBD modele;

    public ControleurVendeurMajQte(int idMag, ConnexionMySQL laConnexion) {
        this.laConnexion = laConnexion;
        this.idMag = idMag;
        this.modele = new VendeurBD(this.laConnexion);
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Ajouter un livre");
            dialog.setHeaderText("Entrez l'ID du livre à ajouter au stock:");
            dialog.setContentText("ID :");
            Optional<String> idLivre = dialog.showAndWait();
            if (!modele.dispo(idMag, idLivre.get(), 1, false)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reussite");
                alert.setHeaderText(null);
                alert.setContentText("La librairie ne possède pas ce livre. Ajoutez-le");
                alert.showAndWait();
                return;
            }
            TextInputDialog dialog2 = new TextInputDialog();
            dialog2.setTitle("Ajouter un livre");
            dialog2.setHeaderText("Entrez la quantité à ajouter :");
            dialog2.setContentText("Quantité :");
            Optional<String> qte = dialog2.showAndWait();

            if (!idLivre.isEmpty() && !qte.isEmpty() && modele.existe(idLivre.get())) {
                int qteInt = Integer.valueOf(qte.get());
                modele.majQte(idMag, idLivre.get(), qteInt, false);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reussite");
                alert.setHeaderText(null);
                alert.setContentText("quantité mise à jour");
                alert.showAndWait();
            }
        } catch (SQLException e) {

        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reussite");
            alert.setHeaderText(null);
            alert.setContentText("quantité mise à jour");
            alert.showAndWait();
        }

    }
}
