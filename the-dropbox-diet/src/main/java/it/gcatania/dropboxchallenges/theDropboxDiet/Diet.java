/**
 * 
 */
package it.gcatania.dropboxchallenges.theDropboxDiet;

import java.util.ArrayList;
import java.util.Arrays;
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

    public Diet(DietItem... items)
    {
        this(Arrays.asList(items));
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return items.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Diet)
        {
            return ((Diet) obj).items.equals(items);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return items.toString();
    }

}
