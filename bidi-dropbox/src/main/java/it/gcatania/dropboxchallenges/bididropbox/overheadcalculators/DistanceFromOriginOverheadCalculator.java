package it.gcatania.dropboxchallenges.bididropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.bididropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.bididropbox.model.Coordinates;
import it.gcatania.dropboxchallenges.bididropbox.model.DropBox;


/**
 * @author gcatania
 */
public class DistanceFromOriginOverheadCalculator implements OverheadCalculator
{

    /**
     * calculates the overhead as the distance between the upper right corner of the rectangle and the diagonal
     */
    @Override
    public int getOverhead(DropBox dropBox, CartesianRectangle rect)
    {
        Coordinates upperRight = rect.getUpperRight();
        int x = upperRight.getX();
        int y = upperRight.getY();
        return x * x + y * y;
    }

}
