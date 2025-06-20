import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ControleurAcheterVendeur implements EventHandler<ActionEvent>{
    
    private LivreExpress appli;
    private Integer qte;
    private Livre selection;

    public ControleurAcheterVendeur(LivreExpress vue, Integer qte, Livre selection){
        this.appli = vue;
        this.qte = qte;
        this.selection = selection;
    }

    @Override
    public void handle(ActionEvent e){
        System.out.println(selection);
        if (selection != null) {
            this.appli.getPanier().ajouterDetailsCommande(selection, qte);
            this.appli.affichePageVendeur();
            this.appli.getResumeCommande().getItems().setAll(this.appli.getPanier().getDetailsCommande());

        }
    }

}