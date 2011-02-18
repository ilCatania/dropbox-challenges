/**
 * 
 */
package it.gcatania.dropboxchallenges.theDropboxDiet;

import java.util.Comparator;


/**
 * @author gcatania
 */
public class DietItemNameComparator implements Comparator<DietItem>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(DietItem arg0, DietItem arg1)
    {
        return arg0.itemName.compareTo(arg1.itemName);
    }

}
