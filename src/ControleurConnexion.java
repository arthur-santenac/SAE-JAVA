import javafx.event.EventHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.w3c.dom.events.Event;
import javafx.event.ActionEvent ;
import javafx.scene.control.Button;



public class ControleurConnexion implements EventHandler<ActionEvent> {
    
    private LivreExpress appli;
    
    public ControleurConnexion(LivreExpress appli){
        this.appli = appli;
    }
    
    @Override
    public void handle(ActionEvent event){
        Button button = (Button) (event.getSource());
        if (button.getText().equals("Déconnexion")) {
            this.appli.afficheConnexion();
        } else {
        try {
			st = laConnexion.createStatement();
			ResultSet set = st.executeQuery("select * from CONNEXION natural join CLIENT");
			while (set.next()) {
				if (email.equals(set.getString(2)) && mdp.equals(set.getString(3))) {
					app.setUtilisateur(new Client(set.getString(5), set.getString(6), set.getString(7), set.getString(8), set.getString(9), set.getInt(1)));
					return set.getString(4);
				}
			}
            // mauvais mdp
		} catch (SQLException e) {
            // mauvais mdp
		}
        }

    }
}


