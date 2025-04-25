import org.junit.Assert;
import org.junit.Test;

public class MagasinTest{
    
    @Test
    public void TestGetIdMag(){
        Magasin leclerc = new Magasin(10,"Leclerc","Olivet");
        assert leclerc.getIdMag() == 10;
    }


    @Test
    public void TestGetNomMag(){
        Magasin leclerc = new Magasin(10,"Leclerc","Olivet");
        assert leclerc.getNomMag() == "Leclerc";
    }


    @Test
    public void TestGetVilleMag(){
        Magasin leclerc = new Magasin(10,"Leclerc","Olivet");
        assert leclerc.getVilleMag() == "Olivet";
    }

    





}