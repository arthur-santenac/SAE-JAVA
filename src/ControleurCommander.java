import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;


public class ControleurCommander implements EventHandler<ActionEvent>{
    
    private LivreExpress appli;

    public ControleurCommander(LivreExpress appli){
        this.appli = appli;
    }

    @Override
    public void handle(ActionEvent e){
        if (this.appli.getPanier().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez rien dans votre panier");
            alert.showAndWait();
        } else if (this.appli.getCBMagasins().getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez pas choisi de magasin");
            alert.showAndWait();
        } else if (this.appli.getModeLivraison().getSelectedToggle() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez pas choisi de mode de livraison");
            alert.showAndWait();
        }  else {
            this.appli.setEnLigne('O');
            this.appli.setMagasin(this.appli.getCBMagasins().getValue());
            if (this.appli.getModeLivraison().getSelectedToggle().equals(this.appli.getLivraisonDomicile())) {
                this.appli.setLivraison('C');
            } else {
                this.appli.setLivraison('M');
            }
            try {
                int maxNumCom = this.appli.getCommandeBD().maxNumCom() + 1;
                this.appli.getCommandeBD().insererCommande(maxNumCom, this.appli.getPanier().getEnLigne(), this.appli.getPanier().getLivraison(), this.appli.getUtilisateur().getIdCli(),
                        this.appli.getMagasin().getIdMag());
                for (int i = 0; i < this.appli.getPanier().size(); i++) {
                    this.appli.getCommandeBD().insererDetailCommande(maxNumCom, i + 1,
                            this.appli.getPanier().getDetailsCommande().get(i).getQte(),
                            this.appli.getPanier().getDetailsCommande().get(i).getPrixVente(),
                            this.appli.getPanier().getDetailsCommande().get(i).getLivre().getIsbn());
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Opération");
                alert.setHeaderText(null);
                alert.setContentText("Votre commande est en cours de livraison");
                alert.showAndWait();
                this.appli.nouveauPanier();
                this.appli.affichePageClient();
            } catch (SQLException ex) {
                System.out.println("erreur sql");
                System.console().readLine();
            }
        }
        
    }
}

