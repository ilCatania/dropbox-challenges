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
    public long getOverhead(DropBox dropBox, CartesianRectangle rect)
    {
        long area = dropBox.getAreaWith(rect);
        long containedRectanglesArea = dropBox.getContainedRectanglesArea() + rect.getArea();
        long freeSpace = area - containedRectanglesArea;
        return freeSpace;
    }

}
