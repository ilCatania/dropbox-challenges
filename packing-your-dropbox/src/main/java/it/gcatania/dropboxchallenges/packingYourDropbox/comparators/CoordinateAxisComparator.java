/**
 * 
 */
package it.gcatania.dropboxchallenges.packingYourDropbox.comparators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.Coordinates;

import java.util.Comparator;


/**
 * compares coordinates by their x value first, and by their y value if the x's are equal.
 * @author gcatania
 */
public class CoordinateAxisComparator implements Comparator<Coordinates>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Coordinates arg0, Coordinates arg1)
    {
        int signX = Long.signum(arg0.getX() - arg1.getX());
        if (signX == 0)
        {
            return Long.signum(arg0.getY() - arg1.getY());
        }
        return signX;
    }

}
