import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ControleurBoutonCatalogue implements EventHandler<ActionEvent>{
    
    private LivreExpress appli;
    private Integer qte;
    private Livre selection;

    public ControleurBoutonCatalogue(LivreExpress vue, Integer qte, Livre selection){
        this.appli = vue;
        this.qte = qte;
        this.selection = selection;
    }

    @Override
    public void handle(ActionEvent e){
        this.appli.getPanier().ajouterDetailsCommande(selection, qte);;
    }


}