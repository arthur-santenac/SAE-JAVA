import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurAdminStats implements EventHandler<ActionEvent>{
    
    private LivreExpress appli;

    public ControleurAdminStats(LivreExpress appli){
        this.appli = appli;
    }


    public void handle(ActionEvent e){
        this.appli.affichePageAdminStats();
    }

}
