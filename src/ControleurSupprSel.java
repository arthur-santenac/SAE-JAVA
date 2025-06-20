import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;


public class ControleurSupprSel implements EventHandler<ActionEvent>{
    
    private LivreExpress appli;

    public ControleurSupprSel(LivreExpress appli){
        this.appli = appli;
    }

    @Override
    public void handle(ActionEvent e){
        DetailCommande detail = this.appli.getListeArticles().getSelectionModel().getSelectedItem();
        this.appli.getPanier().getDetailsCommande().remove(detail);
        this.appli.affichePagePanier();
    }

}