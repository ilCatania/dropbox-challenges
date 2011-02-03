package it.gcatania.dropboxchallenges.bididropbox.overheadcalculators;

/**
 * @author gcatania
 */
public class DropBoxOverheadCalculator extends CompositeOverheadCalculator
{

    public DropBoxOverheadCalculator()
    {
        super(new DistanceFromOriginOverheadCalculator(), new AreaOverheadCalculator());
    }
}
