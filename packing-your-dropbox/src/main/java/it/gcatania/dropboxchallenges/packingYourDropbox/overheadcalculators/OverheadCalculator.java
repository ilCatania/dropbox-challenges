package it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;


/**
 * @author gcatania
 */
public interface OverheadCalculator
{

    /**
     * calculates the overhead introduced by adding the input cartesian rectangle to a dropbox
     * @param dropbox the drop box that will receive the rectangle
     * @param rect the cartesian rectangle rectangle to add
     * @return the overhead (an integer greater or equal than zero)
     */
    long getOverhead(Dropbox dropbox, CartesianRectangle rect);
}
