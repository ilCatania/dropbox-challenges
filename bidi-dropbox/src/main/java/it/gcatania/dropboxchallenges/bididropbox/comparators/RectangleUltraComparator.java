package it.gcatania.dropboxchallenges.bididropbox.comparators;

import it.gcatania.dropboxchallenges.bididropbox.model.Rectangle;


/**
 * @author gcatania
 */
public class RectangleUltraComparator extends AverageComparator<Rectangle>
{

    @SuppressWarnings("unchecked")
    public RectangleUltraComparator()
    {
        super(new RectangleMaxSideComparator(), new RectanglePerimeterComparator(), new RectangleAreaComparator());
    }
}
