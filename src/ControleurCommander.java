import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ControleurCommander implements EventHandler<ActionEvent>{
    
    private LivreExpress appli;

    public ControleurCommander(LivreExpress appli){
        this.appli = appli;
    }

    @Override
    public void handle(ActionEvent e){
        if (this.appli.getPanier().size() == 0) {

        } else if (this.appli.getModeLivraison().getSelectedToggle() == null) {

        } else if (this.appli.getCBMagasins().getValue() == null) {

        } else {
            System.out.println(this.appli.getModeLivraison().getSelectedToggle());
            this.appli.setEnLigne('1');
            this.appli.setMagasin(this.appli.getCBMagasins().getValue());
            if (true) {
                this.appli.setLivraison('1');
            } else {
                this.appli.setLivraison('0');
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
                this.appli.nouveauPanier();
            } catch (SQLException ex) {
                System.out.println("erreur sql");
                System.console().readLine();
            }
        }
        
    }
}

