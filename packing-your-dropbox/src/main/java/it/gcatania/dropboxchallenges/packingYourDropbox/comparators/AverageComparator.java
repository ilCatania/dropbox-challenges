package it.gcatania.dropboxchallenges.packingYourDropbox.comparators;

import java.util.Comparator;


/**
 * @author gcatania
 */
public class AverageComparator<T> implements Comparator<T>
{

    private final Comparator<T>[] comparators;

    public AverageComparator(Comparator<T>... comparators)
    {
        this.comparators = comparators;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(T o1, T o2)
    {
        int result = 0;
        for (Comparator<T> comparator : comparators)
        {
            result += Integer.signum(comparator.compare(o1, o2));
        }
        return result;
    }

}
