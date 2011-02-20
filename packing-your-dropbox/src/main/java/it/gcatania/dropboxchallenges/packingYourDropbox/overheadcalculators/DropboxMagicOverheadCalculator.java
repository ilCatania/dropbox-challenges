/**
 * 
 */
package it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;


/**
 * a "magic" overhead calculator that calculates overhead by area first, and then by comparing the distance between the
 * rectangle and dropbox's respective upper right corners (reversed)
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
                return -Math.max(rect.getUpperRight().getX(), rect.getUpperRight().getY());
            }
        });
    }
}
