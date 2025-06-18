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
                TextField mdpField = new TextField();
                TextField idMagField = new TextField();
                Button btnListeLib = new Button("Afficher la liste de librairies");
                btnListeLib.setOnAction(new ControleurAfficheListeLib(magasinBD, this.laConnexion));
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));
                grid.add(new Label("Nom :"), 0, 0);
                grid.add(nomField, 1, 0, 3, 1);
                grid.add(new Label("Prénom :"), 0, 1);
                grid.add(prenomField, 1, 1, 3, 1);
                grid.add(new Label("Ville :"), 0, 2);
                grid.add(villeField, 1, 2, 3, 1);
                grid.add(new Label("Code Postal :"), 0, 3);
                grid.add(codePostalField, 1, 3, 3, 1);
                grid.add(new Label("adresse :"), 0, 4);
                grid.add(adresseField, 1, 4, 3, 1);
                grid.add(new Label("Email :"), 0, 5);
                grid.add(emailField, 1, 5, 3, 1);
                grid.add(new Label("Mot de passe :"), 0, 6);
                grid.add(mdpField, 1, 6, 3, 1);
                grid.add(new Label("Id de sa librairie attribuée :"), 0, 7);
                grid.add(idMagField, 1, 7, 3, 1);
                grid.add(btnListeLib, 0, 8);
                
                dialog.getDialogPane().setContent(grid);

                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == boutonAjouter) {
                    if(!nomField.getText().trim().isEmpty() && !prenomField.getText().trim().isEmpty() && !villeField.getText().trim().isEmpty() && !codePostalField.getText().trim().isEmpty() && !adresseField.getText().trim().isEmpty() &&  !emailField.getText().trim().isEmpty()){
                        String nom = nomField.getText();
                        String prenom = prenomField.getText();
                        String ville = villeField.getText();
                        String adresse = adresseField.getText();
                        String cp = codePostalField.getText();
                        String email = emailField.getText();
                        String mdp = mdpField.getText();
                        Integer idMag = Integer.parseInt(idMagField.getText());
                        Client client = new Client(nom, prenom, adresse, cp, ville, clientBD.maxNum()+1);
                        clientBD.insererVendeur(client, email, mdp, idMag);
                        this.appli.affichePageAdminVendeur();
                    }
                    else{
                        Alert erreurAjout = new Alert(AlertType.ERROR);
                        erreurAjout.setTitle("Erreur de création du vendeur");
                        erreurAjout.setHeaderText("Veuillez remplir tous les champs avant d'ajouter le vendeur");
                        erreurAjout.setContentText("Retentez à nouveau");
                        erreurAjout.showAndWait();
                    }
                    
                }
            }catch(SQLException ex){
                System.out.println("Erreur dans l'ajout d'un vendeur");
            }
        }
        else{
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Supprimer un vendeur");
            dialog.setHeaderText("Entrez l'id du vendeur à supprimer :");
            dialog.setContentText("Id :");
            Optional<String> idLib = dialog.showAndWait();                
            if (!idLib.isEmpty()){
                Integer id = Integer.parseInt(idLib.get());
                ButtonType oui = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
                ButtonType non = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", oui, non);
                try{
                    Client c = clientBD.rechercherVendeurParId(id);
                    alert.setTitle("Confirmer la suppresion du vendeur");
                    alert.setHeaderText("Êtes-vous sûr de vouloir supprimer le vendeur :\n "+c.toString());
                    alert.setContentText("Cette action est irréversible.");
                    Optional<ButtonType> res = alert.showAndWait();
                    if (res.isPresent() && res.get() == oui) {
                        try {
                            clientBD.supprimerVendeur(id);
                            this.appli.affichePageAdminVendeur();
                        } catch (SQLException ex) {
                            System.out.println("Erreur dans la suppression du vendeur");
                        }
                    } else {
                        System.out.println("Suppression annulée par l'utilisateur.");
                    }
                }catch(SQLException ex){
                        System.out.println("Erreur dans la recherche du vendeur");
                }
            } 
        }
    }

}
