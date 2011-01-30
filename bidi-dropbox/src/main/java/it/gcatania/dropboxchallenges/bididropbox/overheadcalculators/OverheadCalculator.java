package it.gcatania.dropboxchallenges.bididropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.bididropbox.model.CartesianRectangle;


/**
 * @author gcatania
 */
public interface OverheadCalculator
{

    /**
     * calculates the overhead introduced by adding the input cartesian rectangle to a dropbox
     * @param rect the cartesian rectangle rectangle to add
     * @return the overhead (an integer greater or equal than zero)
     */
    int getOverhead(CartesianRectangle rect);
}
