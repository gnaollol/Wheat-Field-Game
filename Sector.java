package wheatfield;

/**
 *
 * @author Gian Lardizabal
 */
public class Sector {
    private int numBushelsOfWheatGrains;
    
    /**
     * Constructs sector with initial # of wheat
     * @param initialNumBushelsOfWheatGrains 
     */
    public Sector(int initialNumBushelsOfWheatGrains){
        numBushelsOfWheatGrains = initialNumBushelsOfWheatGrains;
    }
    /**
     * 
     * @return numBushelsOfWheatGrains
     */
    public int getNumBushelsOfWheatGrains(){
        return numBushelsOfWheatGrains;
    }
    /**
     * 
     * @return gather 
     */
    public int gather(){
        return gather(0.8);
    }
    /**
     * 
     * @param proportion
     * @return amtGathered
     */
    public int gather(double proportion){
        int amountGathered = (int)(numBushelsOfWheatGrains * proportion);
        numBushelsOfWheatGrains = numBushelsOfWheatGrains - amountGathered;
        return amountGathered;
    }
}
