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

    





}