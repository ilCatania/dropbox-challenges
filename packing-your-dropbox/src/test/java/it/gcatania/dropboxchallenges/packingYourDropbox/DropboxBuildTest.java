package it.gcatania.dropboxchallenges.packingYourDropbox;

import org.testng.annotations.Test;


/**
 * @author gcatania
 */
public class DropboxBuildTest
{

    @Test
    public void testSampleInput()
    {
        DropboxBuild.main(new String[]{"src/test/resources/sample-input.txt" });
    }

}
