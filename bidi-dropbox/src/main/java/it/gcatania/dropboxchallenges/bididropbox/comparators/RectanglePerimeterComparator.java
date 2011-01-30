package it.gcatania.dropboxchallenges.bididropbox.comparators;

import it.gcatania.dropboxchallenges.bididropbox.model.Rectangle;

import java.util.Comparator;


/**
 * @author gcatania
 */
public class RectanglePerimeterComparator implements Comparator<Rectangle>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Rectangle o1, Rectangle o2)
    {
        return o2.getSemiPerimeter() - o1.getSemiPerimeter();
    }

}
