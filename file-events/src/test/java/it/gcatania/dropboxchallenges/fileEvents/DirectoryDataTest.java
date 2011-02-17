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
        Assert.assertTrue(d.contains("/parent/child/nephew"));
        Assert.assertFalse(d.contains("/parent/child2"));
    }
}
