import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class LivreTest {
        @Test
        public void testGetIsbn(){
            Livre leLivreDeLaJungle = new Livre(1,"Le livre de la jungle",281,"01/01/1999",19.00, 0, null);
            assert leLivreDeLaJungle.getIsbn() == 1;
        }



        @Test
        public void testGetTitre(){
            Livre leLivreDeLaJungle = new Livre(1,"Le livre de la jungle",281,"01/01/1999",19.00, 0, null);
            assertEquals("Le livre de la jungle", leLivreDeLaJungle.getTitre());;
        }

        @Test
        public void testGetNbPages(){
            Livre leLivreDeLaJungle = new Livre(1,"Le livre de la jungle",281,"01/01/1999",19.00, 0, null);
            assertEquals(281, leLivreDeLaJungle.getNbPages());
        }

        @Test
        public void testGetDatePubli(){
            Livre leLivreDeLaJungle = new Livre(1,"Le livre de la jungle",281,"01/01/1999",19.00, 0, null);
            assertEquals("01/01/1999", leLivreDeLaJungle.getDatePubli());
        }

        @Test
        public void testGetPrix(){
            Livre leLivreDeLaJungle = new Livre(1,"Le livre de la jungle",281,"01/01/1999",19.99, 0, null);
            assertEquals(19.99, leLivreDeLaJungle.getPrix(),0.001);
        }


        @Test
        public void testAjouterAuteur(){
            Livre leLivreDeLaJungle = new Livre(1,"Le livre de la jungle",281,"01/01/1999",19.00, 0, null);
            Auteur auteur = new Auteur(450,"Shakespeare","23/04/1564","23/04/1616");
            List<Auteur> listeAttendu = new ArrayList<>();
            listeAttendu.add(auteur);
            assertEquals(true , leLivreDeLaJungle.ajouterAuteur(auteur));
        }

        @Test
        public void testAjouterEditeur(){
            Livre leLivreDeLaJungle = new Livre(1,"Le livre de la jungle",281,"01/01/1999",19.00, 0, null);
            Editeur editeur = new Editeur(3007,"Gallimard");
            List<Editeur> listeAttendu = new ArrayList<>();
            listeAttendu.add(editeur);
            assertEquals(true , leLivreDeLaJungle.ajouterEditeur(editeur));
        }
        


        @Test
        public void testGetAuteurs(){
            Livre leLivreDeLaJungle = new Livre(1,"Le livre de la jungle",281,"01/01/1999",19.00, 0, null);
            Auteur auteur = new Auteur(450,"Shakespeare","23/04/1564","23/04/1616");
            leLivreDeLaJungle.ajouterAuteur(auteur);
            List<Auteur> listeAttendu = new ArrayList<>();
            listeAttendu.add(auteur);
            assertEquals(listeAttendu, leLivreDeLaJungle.getAuteurs());
        }

        @Test
        public void testGetEditeurs(){
            Livre leLivreDeLaJungle = new Livre(1,"Le livre de la jungle",281,"01/01/1999",19.00, 0, null);
            Editeur editeur = new Editeur(3007,"Gallimard");
            leLivreDeLaJungle.ajouterEditeur(editeur);
            List<Editeur> listeAttendu = new ArrayList<>();
            listeAttendu.add(editeur);
            assertEquals(listeAttendu, leLivreDeLaJungle.getEditeurs());
        }

        
        

}    






