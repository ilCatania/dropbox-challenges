package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * @author gcatania
 */
public class DirectoryDataTest
{

    @Test
    public void testContains()
    {
        DirectoryData d = new DirectoryData("/parent/child");
        Assert.assertTrue(d.contains(new DirectoryData("/parent/child/nephew")));
        Assert.assertFalse(d.contains(new DirectoryData("/parent/child2")));
    }
}
