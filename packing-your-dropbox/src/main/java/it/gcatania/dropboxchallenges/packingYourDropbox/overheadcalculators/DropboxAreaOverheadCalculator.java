package it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;


/**
 * @author gcatania
 */
public class DropboxAreaOverheadCalculator implements OverheadCalculator<Long>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getOverhead(Dropbox dropbox, CartesianRectangle rect)
    {
        return dropbox.getAreaWith(rect) - dropbox.getArea();
    }
}
