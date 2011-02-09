package it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;


/**
 * @author gcatania
 */
public class FreeSpaceOverheadCalculator implements OverheadCalculator
{

    /**
     * {@inheritDoc}
     */
    @Override
    public long getOverhead(Dropbox dropbox, CartesianRectangle rect)
    {
        long area = dropbox.getAreaWith(rect);
        long containedRectanglesArea = dropbox.getContainedRectanglesArea() + rect.getArea();
        long freeSpace = area - containedRectanglesArea;
        return freeSpace;
    }

}
