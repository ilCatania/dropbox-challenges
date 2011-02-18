/**
 * 
 */
package it.gcatania.dropboxchallenges.packingYourDropbox.comparators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.Coordinates;


/**
 * @author gcatania
 */
public class CoordinateCompositeComparator extends SequentialComparator<Coordinates>
{

    @SuppressWarnings("unchecked")
    public CoordinateCompositeComparator()
    {
        super(new CoordinateDistanceFromOriginComparator(), new CoordinateAxisComparator());
    }
}
