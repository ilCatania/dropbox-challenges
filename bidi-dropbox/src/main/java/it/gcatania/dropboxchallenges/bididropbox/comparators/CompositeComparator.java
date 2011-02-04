package it.gcatania.dropboxchallenges.bididropbox.comparators;

import java.util.Comparator;


/**
 * @author gcatania
 */
public class CompositeComparator<T> implements Comparator<T>
{

    private final Comparator<T>[] comparators;

    public CompositeComparator(Comparator<T>... comparators)
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
