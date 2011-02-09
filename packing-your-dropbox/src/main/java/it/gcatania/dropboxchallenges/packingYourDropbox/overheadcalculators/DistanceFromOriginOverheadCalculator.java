package it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Coordinates;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;


/**
 * @author gcatania
 */
public class DistanceFromOriginOverheadCalculator implements OverheadCalculator
{

    /**
     * calculates the overhead as the distance between the upper right corner of the rectangle and the diagonal
     */
    @Override
    public long getOverhead(Dropbox dropbox, CartesianRectangle rect)
    {
        Coordinates upperRight = rect.getUpperRight();
        long x = upperRight.getX();
        long y = upperRight.getY();
        return x * x + y * y;
    }

}
