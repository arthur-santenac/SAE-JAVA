import javafx.event.EventHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class ControleurCreerCompte implements EventHandler<ActionEvent> {

    private LivreExpress appli;
    private TextField email;
    private TextField mdp;
    private TextField nom;
    private TextField prenom;
    private TextField adresse;
    private TextField ville;
    private TextField codePostal;

    public ControleurCreerCompte(LivreExpress appli, TextField email, TextField mdp, TextField nom, TextField prenom, TextField adresse, TextField ville, TextField codePostal) {
        this.appli = appli;
        this.email = email;
        this.mdp = mdp;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.ville = ville;
        this.codePostal = codePostal;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            int idcli = this.appli.getClientBD().maxNum() + 1;
            PreparedStatement ps = this.appli.getConnexion().prepareStatement("insert into CLIENT values (?,?,?,?,?,?)");
            ps.setInt(1, idcli);
            ps.setString(2, nom.getText());
            ps.setString(3, prenom.getText());
            ps.setString(4, adresse.getText());
            ps.setInt(5, Integer.parseInt(codePostal.getText()));
            ps.setString(6, ville.getText());
            ps.executeUpdate();

            ps = this.appli.getConnexion().prepareStatement("insert into CONNEXION values (?,?,?,?)");
            ps.setString(1, email.getText());
            ps.setString(2, mdp.getText());
            ps.setInt(3, idcli);
            ps.setString(4, "client");
            ps.executeUpdate();
            this.appli.afficheConnexion();
        } catch (NumberFormatException e) {
            this.appli.erreurCreerCompte();
        } catch (SQLException e) {
            this.appli.erreurCreerCompte();
        }
    }
}