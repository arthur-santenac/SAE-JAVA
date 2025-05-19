import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class MagasinTest{
    
    @Test
    public void testGetIdMag(){
        Magasin leclerc = new Magasin(10,"Leclerc","Olivet");
        assert leclerc.getIdMag() == 10;
    }


    @Test
    public void testGetNomMag(){
        Magasin leclerc = new Magasin(10,"Leclerc","Olivet");
        assert leclerc.getNomMag().equals("Leclerc");
    }


    @Test
    public void testGetVilleMag(){
        Magasin leclerc = new Magasin(10,"Leclerc","Olivet");
        assert leclerc.getVilleMag().equals("Olivet");
    }


    @Test
    public void testAjouteCommande(){
        Magasin leclerc = new Magasin(10,"Leclerc","Olivet");
        Magasin auchan = new Magasin(2, "Auchan", "Saint-Jean de la ruelle");
        Client arthur = new Client("Santenac", "Arthur", "Rue des champs", 45000, "Orléans",1);
        Commande commande = new Commande(1, "20/5/2025", "O", "M", leclerc,arthur);
        Commande commande2 = new Commande(2, "20/5/2025", "O", "M", auchan,arthur);
        assertEquals(false, leclerc.ajouteCommande(commande2));
        assertEquals(true, leclerc.ajouteCommande(commande));
        assertEquals(true, auchan.ajouteCommande(commande2));
    }

    @Test
    public void testAjouteLivre(){
        Magasin leclerc = new Magasin(10,"Leclerc","Olivet");
        Livre lesMiserables = new Livre(1, "Les Misérables", 200, "31/03/1862", 11.90);
        Livre leLivreDeLaJungle = new Livre(1,"Le livre de la jungle",281,"01/01/1999",19.00);
        assertEquals(true, leclerc.ajouterLivre(leLivreDeLaJungle));
        assertEquals(true, leclerc.ajouterLivre(lesMiserables));
        assertEquals(false, leclerc.ajouterLivre(leLivreDeLaJungle));
    }


    @Test
    public void getCommandes(){
        Client arthur = new Client("Santenac", "Arthur", "Rue des champs", 45000, "Orléans",1);
        Magasin leclerc = new Magasin(10,"Leclerc","Olivet");
        Magasin auchan = new Magasin(2, "Auchan", "Saint-Jean de la ruelle");
        Livre lesMiserables = new Livre(1, "Les Misérables", 200, "31/03/1862", 11.90);
        Livre leLivreDeLaJungle = new Livre(1,"Le livre de la jungle",281,"01/01/1999",19.00);
        leclerc.ajouterLivre(leLivreDeLaJungle);
        leclerc.ajouterLivre(lesMiserables);
        auchan.ajouterLivre(lesMiserables);
        arthur.choisirMagasin(leclerc);
        arthur.PasserUnecommande("Les Miserables", 1,ModeReception.ENLIGNE,leclerc);
        arthur.choisirMagasin(auchan);
        arthur.PasserUnecommande("Les Miserables", 1,ModeReception.ENLIGNE,auchan);
        List<Commande> listeAttendu = leclerc.getCommandes();
        assertEquals(listeAttendu, leclerc.getCommandes());

    }
    





}