/**
 * 
 */
package it.gcatania.dropboxchallenges.theDropboxDiet;

/**
 * @author gcatania
 */
public class DietItem
{

    public final String itemName;

    public final int cals;

    public DietItem(String itemName, int cals)
    {
        this.itemName = itemName;
        this.cals = cals;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return 1 + 11 * itemName.hashCode() + 17 * cals;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof DietItem)
        {
            DietItem other = (DietItem) obj;
            return other.cals == cals && other.itemName.equals(itemName);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return itemName + " " + cals;
    }
}
