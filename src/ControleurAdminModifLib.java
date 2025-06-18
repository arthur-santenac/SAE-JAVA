import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;

public class ControleurAdminModifLib implements EventHandler<ActionEvent>{

    private LivreExpress appli;

    private ConnexionMySQL laConnexion;
    
    public ControleurAdminModifLib(LivreExpress appli, ConnexionMySQL laConnexion){
        this.appli = appli;
        this.laConnexion =  laConnexion;
    }

    @Override
    public void handle(ActionEvent e) {
        Button bouton = (Button) e.getSource();
        AdminBD adminBD = new AdminBD(this.laConnexion);
        MagasinBD magasinBD = new MagasinBD(this.laConnexion);
        if(bouton.getText().equals("Ajouter une librairie")){
            try{
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Ajouter une librairie");
                dialog.setHeaderText("Entrez le nom de la librairie à ajouter :");
                dialog.setContentText("Nom :");
                Optional<String> nomLib = dialog.showAndWait();
                TextInputDialog dialog2 = new TextInputDialog();
                dialog2.setTitle("Ajouter un livre");
                dialog2.setHeaderText("Entrez la ville de la Librairie");
                dialog2.setContentText("Ville :");
                Optional<String> villeLib = dialog2.showAndWait();
                
                if (!nomLib.isEmpty() && !villeLib.isEmpty()){
                    Magasin m = new Magasin(adminBD.maxNumMagasin()+1, nomLib.get(), villeLib.get());
                    adminBD.insererLibrairie(m);
                    this.appli.affichePageAdminLib();
                } 
            }catch(SQLException ex){
                System.out.println("Erreur dans l'ajout d'une librairie");
            }
        }
        else{
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Supprimer une librairie");
            dialog.setHeaderText("Entrez l'id de la librairie à supprimer :");
            dialog.setContentText("Id :");
            Optional<String> idLib = dialog.showAndWait();                
            if (!idLib.isEmpty()){
                Integer id = Integer.parseInt(idLib.get());
                ButtonType oui = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
                ButtonType non = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", oui, non);
                try{
                    Magasin m = magasinBD.rechercherMagasinParId(id);
                    alert.setTitle("Confirmer la suppresion de la librairie");
                    alert.setHeaderText("Êtes-vous sûr de vouloir supprimer la librairie :\n "+m.getNomMag()+" situé à "+m.getVilleMag());
                    alert.setContentText("Cette action est irréversible.");
                    Optional<ButtonType> res = alert.showAndWait();
                    if (res.isPresent() && res.get() == oui) {
                        try {
                            adminBD.supprimerLibrairie(id);
                            this.appli.affichePageAdminLib();  
                        } catch (SQLException ex) {
                            System.out.println("Erreur dans la suppression de la librairie");
                        }
                    } else {
                        System.out.println("Suppression annulée par l'utilisateur.");
                    }
                }catch(SQLException ex){
                        System.out.println("Erreur dans la recherche de la librairie");
                }
            } 
        }
    }
    
}
