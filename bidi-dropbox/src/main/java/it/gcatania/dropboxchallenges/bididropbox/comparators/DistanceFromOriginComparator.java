package it.gcatania.dropboxchallenges.bididropbox.comparators;

import it.gcatania.dropboxchallenges.bididropbox.model.Coordinates;

import java.util.Comparator;


/**
 * @author gcatania
 */
public class DistanceFromOriginComparator implements Comparator<Coordinates>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Coordinates o1, Coordinates o2)
    {
        long x1 = o1.getX();
        long y1 = o1.getY();
        long squareDistance1 = x1 * x1 + y1 * y1;
        long x2 = o2.getX();
        long y2 = o2.getY();
        long squareDistance2 = x2 * x2 + y2 * y2;
        return Long.signum(squareDistance1 - squareDistance2);
    }

}
