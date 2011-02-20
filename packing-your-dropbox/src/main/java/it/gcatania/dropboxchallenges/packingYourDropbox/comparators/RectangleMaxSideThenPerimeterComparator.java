package it.gcatania.dropboxchallenges.packingYourDropbox.comparators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.Rectangle;


/**
 * @author gcatania
 */
public class RectangleMaxSideThenPerimeterComparator extends AverageComparator<Rectangle>
{

    @SuppressWarnings("unchecked")
    public RectangleMaxSideThenPerimeterComparator()
    {
        super(new RectangleMaxSideComparator(), new RectanglePerimeterComparator(), new RectangleAreaComparator());
    }
}
