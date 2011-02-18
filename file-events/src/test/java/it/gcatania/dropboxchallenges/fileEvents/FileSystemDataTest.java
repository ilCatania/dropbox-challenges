package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * @author gcatania
 */
public class FileSystemDataTest
{

    @Test
    public void testSameName()
    {
        Assert.assertTrue(new DirectoryData("/a/b/n").sameName("/a/b/n"));
        Assert.assertTrue(new DirectoryData("/a/b/n").sameName("/a/n"));
        Assert.assertTrue(new DirectoryData("/a/b/n").sameName("/n"));
        Assert.assertTrue(new DirectoryData("/n").sameName("/a/b/n"));
        Assert.assertTrue(new DirectoryData("/a/b/n").sameName("/a/b/n"));
        Assert.assertTrue(new DirectoryData("/").sameName("/"));
        Assert.assertFalse(new DirectoryData("/a/b/n").sameName("/a/b/n1"));
        Assert.assertFalse(new DirectoryData("/a/b/n").sameName("/a/n1"));
        Assert.assertFalse(new DirectoryData("/a/b/n").sameName("/n1"));
        Assert.assertFalse(new DirectoryData("/n").sameName("/a/b/n1"));
        Assert.assertFalse(new DirectoryData("/a/b/n").sameName("/a/b/n1"));
        Assert.assertFalse(new DirectoryData("/").sameName("/1"));
    }

    @Test
    public void testSameParentPath()
    {
        Assert.assertTrue(new DirectoryData("/a/b/n").sameParentPath("/a/b/n"));
        Assert.assertTrue(new DirectoryData("/a/b/n").sameParentPath("/a/b/n1"));
        Assert.assertTrue(new DirectoryData("/").sameParentPath("/"));
        Assert.assertFalse(new DirectoryData("/a/b1/n").sameParentPath("/a/b/n"));
        Assert.assertFalse(new DirectoryData("/a/b/n").sameParentPath("/a/b"));
        Assert.assertFalse(new DirectoryData("/a/b").sameParentPath("/a/b/n"));
        Assert.assertFalse(new DirectoryData("/").sameParentPath("/1"));
    }

}
