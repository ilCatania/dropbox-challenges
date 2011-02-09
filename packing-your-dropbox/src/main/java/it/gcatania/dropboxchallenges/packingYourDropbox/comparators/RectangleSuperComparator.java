package it.gcatania.dropboxchallenges.packingYourDropbox.comparators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.Rectangle;


/**
 * @author gcatania
 */
public class RectangleSuperComparator extends SequentialComparator<Rectangle>
{

    @SuppressWarnings("unchecked")
    public RectangleSuperComparator()
    {
        super(new RectangleAreaComparator(), new RectangleMaxSideComparator());
    }

}
