import java.util.Comparator;
import java.lang.Math;

public class ComparerLivreParPrixCroissant implements Comparator<Livre>{
    
    @Override
    public int compare(Livre l1, Livre l2){
        int val1 = (int) Math.round(l1.getPrix());
        int val2 = (int) Math.round(l2.getPrix());
        return val1-val2;
    }
}
