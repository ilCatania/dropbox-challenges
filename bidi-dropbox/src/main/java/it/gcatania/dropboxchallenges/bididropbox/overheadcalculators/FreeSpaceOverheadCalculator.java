package it.gcatania.dropboxchallenges.bididropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.bididropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.bididropbox.model.DropBox;


/**
 * @author gcatania
 */
public class FreeSpaceOverheadCalculator implements OverheadCalculator
{

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOverhead(DropBox dropBox, CartesianRectangle rect)
    {
        int area = dropBox.getAreaWith(rect);
        int containedRectanglesArea = dropBox.getContainedRectanglesArea() + rect.getArea();
        int freeSpace = area - containedRectanglesArea;
        return freeSpace;
    }

}
