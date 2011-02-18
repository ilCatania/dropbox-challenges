package it.gcatania.dropboxchallenges.theDropboxDiet;

import it.gcatania.dropboxchallenges.theDropboxDiet.DietBuild;

import org.testng.annotations.Test;


/**
 * 
 */

/**
 * @author gcatania
 */
public class DietBuildTest
{
    @Test
    public void testSampleInput1()
    {
        DietBuild.main(new String[] { "src/test/resources/sample-input-1.txt" });
    }

    @Test
    public void testSampleInput2()
    {
        DietBuild.main(new String[] { "src/test/resources/sample-input-2.txt" });
    }

}
