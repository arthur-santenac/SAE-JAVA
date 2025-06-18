import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;

public class ControleurAdminModifVendeur implements EventHandler<ActionEvent>{

    private LivreExpress appli;

    private ConnexionMySQL laConnexion;
    
    public ControleurAdminModifVendeur(LivreExpress appli, ConnexionMySQL laConnexion){
        this.appli = appli;
        this.laConnexion =  laConnexion;
    }

    @Override
    public void handle(ActionEvent e) {
        Button bouton = (Button) e.getSource();
        AdminBD adminBD = new AdminBD(this.laConnexion);
        MagasinBD magasinBD = new MagasinBD(this.laConnexion);
        ClientBD clientBD = new ClientBD(this.laConnexion);
        if(bouton.getText().equals("Ajouter un vendeur")){
            try{
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Ajouter un vendeur");
                dialog.setHeaderText("Veuillez entrer les informations du vendeur");
                ButtonType boutonAjouter = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(boutonAjouter, ButtonType.CANCEL);
                TextField nomField = new TextField();
                TextField prenomField = new TextField();
                TextField villeField = new TextField();
                TextField adresseField = new TextField();
                TextField codePostalField = new TextField();
                TextField emailField = new TextField();
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));
                grid.add(new Label("Nom :"), 0, 0);
                grid.add(nomField, 1, 0);
                grid.add(new Label("Téléphone :"), 0, 4);
                grid.add(prenomField, 1, 4);
                grid.add(new Label("Ville :"), 0, 1);
                grid.add(villeField, 1, 1);
                grid.add(new Label("Code Postal :"), 0, 3);
                grid.add(codePostalField, 1, 3);
                grid.add(new Label("Rue :"), 0, 2);
                grid.add(adresseField, 1, 2);
                grid.add(new Label("Email :"), 0, 5);
                grid.add(emailField, 1, 5);

                dialog.getDialogPane().setContent(grid);

                // Affichage de la boîte
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == boutonAjouter) {
                    String nom = nomField.getText();
                    String prenom = prenomField.getText();
                    String ville = villeField.getText();
                    String adresse = adresseField.getText();
                    String cp = codePostalField.getText();
                    String email = emailField.getText();
                    Magasin m = new Magasin(adminBD.maxNumMagasin()+1, nom, ville);
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
                            this.appli.affichePageAdminVendeur(); 
                            clientBD.supprimerVendeur(id); 
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
