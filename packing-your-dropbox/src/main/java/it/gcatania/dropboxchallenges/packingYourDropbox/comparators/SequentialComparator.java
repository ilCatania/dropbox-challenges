package it.gcatania.dropboxchallenges.packingYourDropbox.comparators;

import java.util.Comparator;


/**
 * a composite comparator that returns the first non-zero value of child comparators
 * @author gcatania
 */
public class SequentialComparator<T> implements Comparator<T>
{

    private final Comparator<T>[] comparators;

    public SequentialComparator(Comparator<T>... comparators)
    {
        this.comparators = comparators;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(T o1, T o2)
    {
        for (Comparator<T> comparator : comparators)
        {
            int result = comparator.compare(o1, o2);
            if (result != 0)
            {
                return result;
            }
        }
        return 0;
    }

}
