import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;

public class ControleurAfficheListeLib implements EventHandler<ActionEvent>{

    private MagasinBD magasinBD;

    private ConnexionMySQL laConnexion;
    
    public ControleurAfficheListeLib(MagasinBD magasinBD,  ConnexionMySQL laConnexion){
        this.magasinBD = magasinBD;
        this.laConnexion =  laConnexion;
    }

    @Override
    public void handle(ActionEvent e){
        try{
            List<Magasin> librairies = this.magasinBD.listeDesMagasins();
            Alert alert = new Alert(AlertType.NONE);
            ButtonType ok = new ButtonType("Ok");
            alert.getButtonTypes().add(ok);
            alert.setTitle("Liste des librairies");
            alert.setHeaderText("Voici la liste des librairies");
            String chaine = "";
            for(Magasin m : librairies){
                chaine+= m.getIdMag()+" - "+m.toString()+"\n";
            }
            alert.setContentText(chaine);
            alert.showAndWait();
        }catch(SQLException excep){
            System.out.println("Erreur d'affichage");
        }
    }

}