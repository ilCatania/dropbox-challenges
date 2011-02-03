package it.gcatania.dropboxchallenges.bididropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.bididropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.bididropbox.model.Coordinates;
import it.gcatania.dropboxchallenges.bididropbox.model.DropBox;


/**
 * @author gcatania
 */
public class DropBoxOverheadCalculator implements OverheadCalculator
{

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOverhead(DropBox dropBox, CartesianRectangle rect)
    {
        int areaOverhead = dropBox.getAreaWith(rect) - dropBox.getArea();

        Coordinates upperRight = rect.getUpperRight();
        int distanceFromOriginOverhead = upperRight.getX() * upperRight.getX() + upperRight.getY() * upperRight.getY();

        return areaOverhead + distanceFromOriginOverhead;
    }
}
