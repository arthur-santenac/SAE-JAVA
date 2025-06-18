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

public class ControleurVendeurAjoute implements EventHandler<ActionEvent> {

    private int idMag;
    private ConnexionMySQL laConnexion;
    private VendeurBD modele;

    public ControleurVendeurAjoute(int idMag, ConnexionMySQL laConnexion) {
        this.laConnexion = laConnexion;
        this.idMag = idMag;
        this.modele = new VendeurBD(this.laConnexion);
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Ajouter un livre");
            dialog.setHeaderText("Entrez l'ID du livre à ajouter à la librairie :");
            dialog.setContentText("ID :");
            Optional<String> idLivre = dialog.showAndWait();
            if (modele.dispo(idMag, idLivre.get(), 1, false)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reussite");
                alert.setHeaderText(null);
                alert.setContentText("La librairie possède déjà ce livre. Modifiez la quantité");
                alert.showAndWait();
                return;
            }
            TextInputDialog dialog2 = new TextInputDialog();
            dialog2.setTitle("Ajouter un livre");
            dialog2.setHeaderText("Entrez la quatité à ajouter");
            dialog2.setContentText("Quantité :");
            Optional<String> qte = dialog2.showAndWait();

            if (idLivre.isPresent() && qte.isPresent() && modele.existe(idLivre.get())) {
                int qteInt = Integer.valueOf(qte.get());
                modele.ajouteLivre(idMag, idLivre.get(), qteInt);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reussite");
                alert.setHeaderText(null);
                alert.setContentText("Livre ajouté à la librairie avec succès");
                alert.showAndWait();
            }
        } catch (SQLException e) {
        }
        catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reussite");
                alert.setHeaderText(null);
                alert.setContentText("Entrez un nombre");
                alert.showAndWait();
                return;
        }

    }
}
