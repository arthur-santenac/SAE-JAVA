import org.junit.Assert;
import org.junit.Test;

public class ClientTest{
    
    @Test
        public void testGetIdCl(){
            Client patrick = new Client("Dupond", "Patrick","32 Rue du Bourg", 45000,"Orléans",1);
            assert patrick.getIdCli() == 1;
        }





}