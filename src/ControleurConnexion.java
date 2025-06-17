import javafx.event.EventHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.w3c.dom.events.Event;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControleurConnexion implements EventHandler<ActionEvent> {

    private LivreExpress appli;
    private TextField email;
    private TextField mdp;
    private Statement st;

    public ControleurConnexion(LivreExpress appli, TextField email, TextField mdp) {
        this.appli = appli;
        this.email = email;
        this.mdp = mdp;
    }

    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) (event.getSource());
        if (this.appli.getConnexion() == null) {
            try {
                this.appli.setConnexion(email.getText(), mdp.getText(), "servinfo-maria", "DB" + email.getText());
                this.appli.afficheConnexion();
            } catch (SQLException e) {
                System.out.println("erreur sql");
            } 
        } else if (button.getText().equals("Déconnexion")) {
            this.appli.afficheConnexion();
        } else {
            try {
                st = this.appli.getConnexion().createStatement();
                ResultSet set = st.executeQuery("select * from CONNEXION natural join CLIENT");
                while (set.next()) {
                    if (email.getText().equals(set.getString(2)) && mdp.getText().equals(set.getString(3))) {
                        appli.setUtilisateur(new Client(set.getString(5), set.getString(6), set.getString(7), set.getString(8), set.getString(9), set.getInt(1)));
                        String compte = set.getString(4);
                        if (compte.equals("client")) {
                            appli.affichePageClient();
                        } else if (compte.substring(0, 7).equals("vendeur")) {
                            //a faire
                        } else if (compte.equals("administrateur")) {
                            //a faire
                        }
                    }
                }
                // mauvais mdp
            } catch (SQLException e) {
                // mauvais mdp
            }
        }
    }
}