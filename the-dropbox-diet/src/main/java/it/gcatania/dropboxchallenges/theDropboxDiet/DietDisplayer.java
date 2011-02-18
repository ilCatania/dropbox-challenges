/**
 * 
 */
package it.gcatania.dropboxchallenges.theDropboxDiet;

import java.util.Collections;
import java.util.List;


/**
 * @author gcatania
 */
public class DietDisplayer
{

    /**
     * @param diets
     */
    public void display(List<Diet> diets)
    {
        if (diets.isEmpty())
        {
            System.out.println("no solution");
            return;
        }
        DietItemNameComparator comp = new DietItemNameComparator();
        boolean first = true;
        for (Diet d : diets)
        {
            if (first)
            {
                first = false;
            }
            else
            {
                System.out.println();
            }
            List<DietItem> items = d.getItems();
            Collections.sort(items, comp);
            for (DietItem i : items)
            {
                System.out.println(i.itemName);
            }
        }
    }

}
