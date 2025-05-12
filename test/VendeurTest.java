import org.junit.Assert;
import org.junit.Test;

public class VendeurTest{
    
    @Test
    public void testGetIdVendeur(){
        Vendeur michel = new Vendeur("Marie", "Michel","15 Rue de la République", 45000,"Orléans",1);
        assert michel.getIdVendeur() == 1;
    }





}