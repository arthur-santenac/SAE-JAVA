import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import org.junit.Test;


public class AuteurTest {
    
    @Test
    public void testGetIdAuteur(){
        Auteur auteur = new Auteur(450,"Shakespeare","23/04/1564","23/04/1616");
        assertEquals(450,auteur.getIdAuteur());
    }
}
