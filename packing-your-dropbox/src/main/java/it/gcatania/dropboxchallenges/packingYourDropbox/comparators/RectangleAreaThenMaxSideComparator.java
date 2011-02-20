package it.gcatania.dropboxchallenges.packingYourDropbox.comparators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.Rectangle;


/**
 * @author gcatania
 */
public class RectangleAreaThenMaxSideComparator extends SequentialComparator<Rectangle>
{

    @SuppressWarnings("unchecked")
    public RectangleAreaThenMaxSideComparator()
    {
        super(new RectangleAreaComparator(), new RectangleMaxSideComparator());
    }

}
