package it.gcatania.dropboxchallenges.bididropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.bididropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.bididropbox.model.DropBox;


/**
 * @author gcatania
 */
public class CompositeOverheadCalculator implements OverheadCalculator
{

    private final OverheadCalculator[] calculators;

    public CompositeOverheadCalculator(OverheadCalculator... calculators)
    {
        this.calculators = calculators;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOverhead(DropBox dropBox, CartesianRectangle rect)
    {
        int overhead = 0;
        for (OverheadCalculator c : calculators)
        {
            overhead += c.getOverhead(dropBox, rect);
        }
        return overhead;
    }

}
