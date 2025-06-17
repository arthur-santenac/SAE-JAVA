import javafx.event.EventHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.w3c.dom.events.Event;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControleurPageCreerCompte implements EventHandler<ActionEvent> {

    private LivreExpress appli;


    public ControleurPageCreerCompte(LivreExpress appli) {
        this.appli = appli;
    }

    @Override
    public void handle(ActionEvent event) {
        this.appli.afficheCreerCompte();
    }
}