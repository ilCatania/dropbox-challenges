package it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Coordinates;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;


/**
 * @author gcatania
 */
public class AreaOverheadCalculator implements OverheadCalculator<Long>
{

    /**
     * calculates the overhead as the distance between the upper right corner of the rectangle and the diagonal
     */
    @Override
    public Long getOverhead(Dropbox dropbox, CartesianRectangle rect)
    {
        Coordinates upperRight = rect.getUpperRight();
        return upperRight.getX() * upperRight.getY();
    }

}
