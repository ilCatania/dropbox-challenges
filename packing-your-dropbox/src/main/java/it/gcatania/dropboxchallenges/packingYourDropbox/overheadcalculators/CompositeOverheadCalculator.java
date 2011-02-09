package it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;


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
    public long getOverhead(Dropbox dropbox, CartesianRectangle rect)
    {
        long overhead = 0;
        for (OverheadCalculator c : calculators)
        {
            overhead += c.getOverhead(dropbox, rect);
        }
        return overhead;
    }

}
