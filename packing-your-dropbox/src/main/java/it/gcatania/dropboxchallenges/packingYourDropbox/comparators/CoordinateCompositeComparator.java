/**
 * 
 */
package it.gcatania.dropboxchallenges.packingYourDropbox.comparators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.Coordinates;


/**
 * a composite comparator for coordinates that compares them by their distance from the origin first, and by the
 * respective value
 * of their coordinates afterwards
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
