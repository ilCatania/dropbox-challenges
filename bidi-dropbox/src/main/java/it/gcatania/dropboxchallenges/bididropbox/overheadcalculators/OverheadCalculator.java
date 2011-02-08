package it.gcatania.dropboxchallenges.bididropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.bididropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.bididropbox.model.DropBox;


/**
 * @author gcatania
 */
public interface OverheadCalculator
{

    /**
     * calculates the overhead introduced by adding the input cartesian rectangle to a dropbox
     * @param dropBox the drop box that will receive the rectangle
     * @param rect the cartesian rectangle rectangle to add
     * @return the overhead (an integer greater or equal than zero)
     */
    long getOverhead(DropBox dropBox, CartesianRectangle rect);
}
