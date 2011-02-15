package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEventType;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.CreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.StructuredEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * @author gcatania
 */
public class EventTraslatorTest
{

    private EventTranslator translator = new EventTranslator();

    @Test
    public void testFileAdd1()
    {

        List<RawEvent> raw = new ArrayList<RawEvent>();

        raw.add(new RawEvent(RawEventType.ADD, 0, "/file1.txt", "0"));

        List<StructuredEvent> result = translator.parseMessages(raw);
        Assert.assertEquals(result, Arrays.<StructuredEvent> asList(new CreationEvent("/file1.txt", "0")));
    }

}
