package it.gcatania.dropboxchallenges.packingYourDropbox.comparators;

import java.util.Comparator;


/**
 * a fake comparator, always returns 0
 * @author gcatania
 */
public class FakeComparator<T> implements Comparator<T>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(T o1, T o2)
    {
        return 0;
    }

}
