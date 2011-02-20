/**
 * 
 */
package it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;


/**
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
