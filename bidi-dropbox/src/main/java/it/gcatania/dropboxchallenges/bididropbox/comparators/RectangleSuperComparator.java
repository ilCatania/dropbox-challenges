package it.gcatania.dropboxchallenges.bididropbox.comparators;

import it.gcatania.dropboxchallenges.bididropbox.model.Rectangle;


/**
 * @author gcatania
 */
public class RectangleSuperComparator extends CompositeComparator<Rectangle>
{

    @SuppressWarnings("unchecked")
    public RectangleSuperComparator()
    {
        super(new RectangleAreaComparator(), new RectangleMaxSideComparator());
    }

}
