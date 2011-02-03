package it.gcatania.dropboxchallenges.bididropbox.comparators;

import it.gcatania.dropboxchallenges.bididropbox.model.Rectangle;

import java.util.Comparator;


/**
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
        return getMaxSide(o1) - getMaxSide(o2);
    }

    private static int getMaxSide(Rectangle rect)
    {
        return Math.max(rect.getWidth(), rect.getHeight());
    }

}
