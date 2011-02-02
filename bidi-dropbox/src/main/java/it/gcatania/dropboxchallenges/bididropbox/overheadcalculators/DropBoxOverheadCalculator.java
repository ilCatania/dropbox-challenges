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

        int prevWidth = dropBox.getWidth();
        int prevHeight = dropBox.getHeight();
        int prevArea = dropBox.getArea();

        Coordinates upperRight = rect.getUpperRight();
        int newWidth = Math.max(prevWidth, upperRight.getX());
        int newHeight = Math.max(prevHeight, upperRight.getY());
        int newArea = newWidth * newHeight;

        int areaOverhead = newArea - prevArea;

        int distanceFromOriginOverhead = upperRight.getX() * upperRight.getX() + upperRight.getY() * upperRight.getY();

        return areaOverhead + distanceFromOriginOverhead;
    }
}
