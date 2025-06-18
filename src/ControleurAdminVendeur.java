import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ControleurAdminVendeur implements EventHandler<ActionEvent>{
    
    private LivreExpress appli;

    public ControleurAdminVendeur(LivreExpress vue){
        this.appli = vue;
    }

    @Override
    public void handle(ActionEvent e){
        this.appli.affichePageAdminVendeur();
    }


}
