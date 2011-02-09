package it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators;

/**
 * @author gcatania
 */
public class DropboxOverheadCalculator extends CompositeOverheadCalculator
{

    public DropboxOverheadCalculator()
    {
        super(new DistanceFromOriginOverheadCalculator(), new AreaOverheadCalculator());
    }
}
