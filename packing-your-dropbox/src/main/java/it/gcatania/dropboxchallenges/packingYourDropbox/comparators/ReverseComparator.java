package it.gcatania.dropboxchallenges.packingYourDropbox.comparators;

import java.util.Comparator;


/**
 * @author gcatania
 */
public class ReverseComparator<T> implements Comparator<T>
{

    private final Comparator<T> comparatorToReverse;

    public ReverseComparator(Comparator<T> comparatorToReverse)
    {
        this.comparatorToReverse = comparatorToReverse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(T o1, T o2)
    {
        return comparatorToReverse.compare(o2, o1);
    }
}
