package it.gcatania.dropboxchallenges.packingYourDropbox.comparators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.Rectangle;


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
