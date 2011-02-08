package it.gcatania.dropboxchallenges.bididropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.bididropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.bididropbox.model.DropBox;


/**
 * @author gcatania
 */
public class DropBoxAreaOverheadCalculator implements OverheadCalculator
{

    /**
     * {@inheritDoc}
     */
    @Override
    public long getOverhead(DropBox dropBox, CartesianRectangle rect)
    {
        return dropBox.getAreaWith(rect) - dropBox.getArea();
    }
}
