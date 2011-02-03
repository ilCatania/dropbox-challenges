package it.gcatania.dropboxchallenges.bididropbox.comparators;

import it.gcatania.dropboxchallenges.bididropbox.model.DropBox;

import java.util.Comparator;


/**
 * compares drop boxes by free space, with those having less free space scoring lower
 * @author gcatania
 */
public class DropBoxFreeSpaceComparator implements Comparator<DropBox>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(DropBox o1, DropBox o2)
    {
        return o1.getFreeSpace() - o2.getFreeSpace();
    }

}
