/**
 * 
 */
package it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.PreAllocatingDropbox;


/**
 * a "magic" overhead calculator that calculates overhead by area first, and then with some complex criteria explained
 * in
 * comments
 * @author gcatania
 */
public class DropboxMagicOverheadCalculator extends CompositeOverheadCalculator<Long>
{

    @SuppressWarnings("unchecked")
    public DropboxMagicOverheadCalculator()
    {
        super(new DropboxAreaOverheadCalculator(), new OverheadCalculator<Long>()
        {

            @Override
            public Long getOverhead(Dropbox dropbox, CartesianRectangle rect)
            {
                if (dropbox instanceof PreAllocatingDropbox)
                {
                    // minimize the distance from the pre-allocated perimeter
                    PreAllocatingDropbox pdb = (PreAllocatingDropbox) dropbox;
                    long deltaX = Math.abs(pdb.preAllocatedWidth - rect.getUpperRight().getX());
                    long deltaY = Math.abs(pdb.preAllocatedHeight - rect.getUpperRight().getY());
                    return Math.min(deltaX, deltaY);
                }
                // minimize the distance between the rectangle and dropbox's respective upper right corners
                return -Math.max(rect.getUpperRight().getX(), rect.getUpperRight().getY());
            }
        });
    }
}
