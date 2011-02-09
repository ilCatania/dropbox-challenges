package it.gcatania.dropboxchallenges.packingYourDropbox.comparators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.Rectangle;

import java.util.Comparator;


/**
 * @author gcatania
 */
public class RectangleAreaComparator implements Comparator<Rectangle>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Rectangle o1, Rectangle o2)
    {
        return o1.getArea() - o2.getArea();
    }

}
