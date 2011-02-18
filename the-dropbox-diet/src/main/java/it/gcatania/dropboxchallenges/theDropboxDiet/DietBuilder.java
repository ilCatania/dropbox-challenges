/**
 * 
 */
package it.gcatania.dropboxchallenges.theDropboxDiet;

import java.util.ArrayList;
import java.util.List;


/**
 * @author gcatania
 */
public class DietBuilder
{
    private static final boolean RETURN_FIRST_RESULT = false;

    /**
     * @param items
     * @return
     */
    public List<Diet> findPossibleDietsWith(List<DietItem> items)
    {
        List<Diet> results = new ArrayList<Diet>();
        List<Diet> combinations = new ArrayList<Diet>();

        for (DietItem item : items)
        {
            List<Diet> dietsToAdd = new ArrayList<Diet>();
            for (Diet d : combinations)
            {
                Diet clone = d.clone();
                clone.add(item);
                if (clone.getCurrentCals() == 0)
                {
                    results.add(clone);
                    if (RETURN_FIRST_RESULT)
                    {
                        return results;
                    }
                }
                dietsToAdd.add(clone);
            }
            Diet singleItemDiet = new Diet();
            singleItemDiet.add(item);
            dietsToAdd.add(singleItemDiet);
            combinations.addAll(dietsToAdd);
        }
        return results;
    }

}
