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
        int x1 = o1.getX();
        int y1 = o1.getY();
        int squareDistance1 = x1 * x1 + y1 * y1;
        int x2 = o2.getX();
        int y2 = o2.getY();
        int squareDistance2 = x2 * x2 + y2 * y2;
        return squareDistance1 - squareDistance2;
    }

}
