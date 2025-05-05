import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import org.junit.Test;

public class EditeurTest {
    

    @Test
    public void testGetIdEditeur(){
        Editeur editeur = new Editeur(3007,"Gallimard");
        assertEquals(3007,editeur.getIdAuteur());
    }
}
