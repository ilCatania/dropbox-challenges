package it.gcatania.dropboxchallenges.fileEvents;

import org.testng.annotations.Test;


/**
 * @author gcatania
 */
public class EventTranslationTest
{

    @Test
    public void testSampleInput()
    {
        EventTranslation.main(new String[]{"src/test/resources/sample-input.txt" });
    }
}
