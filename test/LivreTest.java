import org.junit.Test;

import junit.*;

public class LivreTest {
        @Test
        public void TestGetIsbn(){
            Livre Lelivredelajungle = new Livre(1,"Le livre de la jungle",281,"01/01/1999",19);
            assert Lelivredelajungle.getIsbn() == 1;
            assert Lelivredelajungle.getTitre() == "Le livre de la jungle";
        }



        @Test
        public void TestGetTitre(){
            Livre Lelivredelajungle = new Livre(1,"Le livre de la jungle",281,"01/01/1999",19);
            assert Lelivredelajungle.getTitre() == "Le livre de la jungle";
        }
}






