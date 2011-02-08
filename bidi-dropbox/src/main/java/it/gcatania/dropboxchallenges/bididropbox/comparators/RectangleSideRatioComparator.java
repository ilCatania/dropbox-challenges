package it.gcatania.dropboxchallenges.bididropbox.comparators;

import it.gcatania.dropboxchallenges.bididropbox.model.Rectangle;

import java.util.Comparator;


/**
 * @author gcatania
 */
public class RectangleSideRatioComparator implements Comparator<Rectangle>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Rectangle o1, Rectangle o2)
    {
        return Double.compare(getRatio(o1), getRatio(o2));
    }

    private static double getRatio(Rectangle r)
    {
        double max;
        double min;
        if (r.getWidth() > r.getHeight())
        {
            max = r.getWidth();
            min = r.getHeight();
        }
        else
        {
            max = r.getHeight();
            min = r.getWidth();
        }
        return max / min;
    }
}
