package it.gcatania.dropboxchallenges.bididropbox.comparators;

import it.gcatania.dropboxchallenges.bididropbox.model.Rectangle;

import java.util.Comparator;


/**
 * compares two rectangle by first comparing their respective longer sidea, falling back to comparing their respective
 * shorter sides if the longer ones equal.
 * @author gcatania
 */
public class RectangleMaxSideComparator implements Comparator<Rectangle>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Rectangle o1, Rectangle o2)
    {
        long min1;
        long max1;
        if (o1.getWidth() > o1.getHeight())
        {
            max1 = o1.getWidth();
            min1 = o1.getHeight();
        }
        else
        {
            max1 = o1.getHeight();
            min1 = o1.getWidth();
        }
        long min2;
        long max2;
        if (o2.getWidth() > o2.getHeight())
        {
            max2 = o2.getWidth();
            min2 = o2.getHeight();
        }
        else
        {
            max2 = o2.getHeight();
            min2 = o2.getWidth();
        }

        int compareMax = Long.signum(max1 - max2);
        if (compareMax == 0)
        {
            return Long.signum(min1 - min2);
        }
        return compareMax;
    }

}
