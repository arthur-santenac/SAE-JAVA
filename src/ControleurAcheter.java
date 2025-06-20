import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;


public class ControleurAcheter implements EventHandler<ActionEvent>{
    
    private LivreExpress appli;
    private Integer qte;
    private Livre selection;

    public ControleurAcheter(LivreExpress vue, Integer qte, Livre selection){
        this.appli = vue;
        this.qte = qte;
        this.selection = selection;
    }

    @Override
    public void handle(ActionEvent e){
        if (selection != null) {
            this.appli.getPanier().ajouterDetailsCommande(selection, qte);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Vous avez bien ajouter un livre a votre panier");
            alert.showAndWait();
        }
    }

}