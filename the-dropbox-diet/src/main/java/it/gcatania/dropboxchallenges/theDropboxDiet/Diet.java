/**
 * 
 */
package it.gcatania.dropboxchallenges.theDropboxDiet;

import java.util.ArrayList;
import java.util.List;


/**
 * @author gcatania
 */
public class Diet implements Cloneable
{

    private final List<DietItem> items;
    private int currentCals;

    public Diet()
    {
        items = new ArrayList<DietItem>();
        currentCals = 0;
    }

    protected Diet(List<DietItem> items)
    {
        this.items = items;
        for (DietItem i : items)
        {
            currentCals += i.cals;
        }
    }

    public void add(DietItem item)
    {
        items.add(item);
        currentCals += item.cals;
    }

    public List<DietItem> getItems()
    {
        return items;
    }

    public int getCurrentCals()
    {
        return currentCals;
    }

    @Override
    public Diet clone()
    {
        return new Diet(new ArrayList<DietItem>(items));
    }

}
