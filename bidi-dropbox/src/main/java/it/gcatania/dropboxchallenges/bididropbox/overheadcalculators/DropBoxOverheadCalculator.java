package it.gcatania.dropboxchallenges.bididropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.bididropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.bididropbox.model.DropBox;


/**
 * @author gcatania
 */
public class DropBoxOverheadCalculator implements OverheadCalculator
{

    private final DistanceFromOriginOverheadCalculator dfo = new DistanceFromOriginOverheadCalculator();

    private final AreaOverheadCalculator ao = new AreaOverheadCalculator();

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOverhead(DropBox dropBox, CartesianRectangle rect)
    {
        return dfo.getOverhead(dropBox, rect) + ao.getOverhead(dropBox, rect);
    }
}
