package it.gcatania.dropboxchallenges.packingYourDropbox.comparators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;

import java.util.Comparator;


/**
 * compares drop boxes by free space, with those having less free space scoring lower
 * @author gcatania
 */
public class DropboxAreaComparator implements Comparator<Dropbox>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Dropbox o1, Dropbox o2)
    {
        return Long.signum(o1.getArea() - o2.getArea());
    }

}
