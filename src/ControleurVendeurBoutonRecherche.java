import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ControleurVendeurBoutonRecherche implements EventHandler<ActionEvent>{
    
    private LivreExpress appli;
    private Integer qte;
    private Livre selection;

    public ControleurVendeurBoutonRecherche(LivreExpress vue, Integer qte, Livre selection){
        this.appli = vue;
        this.qte = qte;
        this.selection = selection;
    }

    @Override
    public void handle(ActionEvent e){
        if (selection != null) {
            this.appli.getPanier().ajouterDetailsCommande(selection, qte);
        }
    }

}