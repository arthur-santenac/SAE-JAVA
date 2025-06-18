import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ControleurVendeurTransfert implements EventHandler<ActionEvent> {

    private final int idMagDestination;
    private final ConnexionMySQL laConnexion;
    private final VendeurBD modele;

    public ControleurVendeurTransfert(int idMagDestination, ConnexionMySQL laConnexion) {
        this.idMagDestination = idMagDestination;
        this.laConnexion = laConnexion;
        this.modele = new VendeurBD(this.laConnexion);
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            // Étape 1 : saisie de l'ISBN
            TextInputDialog isbnDialog = new TextInputDialog();
            isbnDialog.setTitle("Transfert de livre");
            isbnDialog.setHeaderText("Quel est l'ISBN du livre à transférer ?");
            isbnDialog.setContentText("ISBN :");
            Optional<String> isbnOpt = isbnDialog.showAndWait();
            if (!isbnOpt.isPresent()) return;
            String isbn = isbnOpt.get();

            // Étape 2 : saisie de la quantité
            TextInputDialog qteDialog = new TextInputDialog();
            qteDialog.setTitle("Quantité");
            qteDialog.setHeaderText("Combien d'exemplaires transférer ?");
            qteDialog.setContentText("Quantité :");
            Optional<String> qteOpt = qteDialog.showAndWait();
            if (!qteOpt.isPresent()) return;

            int quantite = Integer.parseInt(qteOpt.get());

            // Étape 3 : récupérer les librairies sources possibles
            List<String> sourcesPossibles = modele.librairiesPossedent(isbn, quantite);
            if (sourcesPossibles.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Aucune librairie ne possède ce livre en quantité suffisante.").showAndWait();
                return;
            }

            // Étape 4 : choix de la librairie source
            ChoiceDialog<String> choixDialog = new ChoiceDialog<>(sourcesPossibles.get(0), sourcesPossibles);
            choixDialog.setTitle("Choix de la librairie source");
            choixDialog.setHeaderText("Choisissez la librairie depuis laquelle transférer le livre :");
            Optional<String> sourceChoisie = choixDialog.showAndWait();
            if (!sourceChoisie.isPresent()) return;

            // Étape 5 : appel de la fonction de transfert
            boolean transfertOK = modele.transfer(
                Integer.parseInt(sourceChoisie.get().substring(1)),
                this.idMagDestination,
                isbn,
                quantite
            );

            if (transfertOK) {
                new Alert(Alert.AlertType.INFORMATION, "Transfert effectué avec succès.").showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Le transfert a échoué.").showAndWait();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Veuillez entrer une quantité valide.").showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erreur SQL : " + e.getMessage()).showAndWait();
        }
    }
}
