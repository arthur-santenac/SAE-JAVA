import org.junit.Assert;
import org.junit.Test;


public class AuteurTest {
    
    @Test
    public void testGetIdAuteur(){
        Auteur auteur = new Auteur(450,"Shakespeare");
        assertEquals(450,auteur.getIdAuteur());
    }
}
