import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class ControleurRetourAdmin implements EventHandler<ActionEvent>{
    
    private LivreExpress appli;

    public ControleurRetourAdmin(LivreExpress vue){
        this.appli = vue;
    }

    @Override
    public void handle(ActionEvent e){
        this.appli.affichePageAdmin();;
    }


}