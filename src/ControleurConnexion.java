import javafx.event.EventHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.w3c.dom.events.Event;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class ControleurConnexion implements EventHandler<ActionEvent> {

    private LivreExpress appli;
    private String email;
    private String mdp;
    private Statement st;
    private ConnexionMySQL laConnexion;

    public ControleurConnexion(ConnexionMySQL laConnexion, LivreExpress appli, String email, String mdp) {
        this.laConnexion = laConnexion;
        this.appli = appli;
        this.email = email;
        this.mdp = mdp;
    }

    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) (event.getSource());
        if (laConnexion == null) {
            try {
                appli.setConnexion(email, mdp, "servinfo-maria", "DB" + email);
            } catch (SQLException e) {

            }
        } else if (button.getText().equals("Déconnexion")) {
            this.appli.afficheConnexion();
        } else {
            try {
                st = laConnexion.createStatement();
                ResultSet set = st.executeQuery("select * from CONNEXION natural join CLIENT");
                while (set.next()) {
                    if (email.equals(set.getString(2)) && mdp.equals(set.getString(3))) {
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