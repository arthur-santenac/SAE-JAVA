import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ControleurAdminLibrairie implements EventHandler<ActionEvent>{
    
    private LivreExpress appli;

    public ControleurAdminLibrairie(LivreExpress vue){
        this.appli = vue;
    }

    @Override
    public void handle(ActionEvent e){
        this.appli.affichePageAdminLib();
    }


}
