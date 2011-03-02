/**
 * Copyright 2011 Gabriele Catania <gabriele.ctn@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
