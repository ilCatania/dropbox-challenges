package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.FileSystemData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEventType;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.CreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryDeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileDeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.StructuredEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
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
    public void testFileAdd()
    {
        List<RawEvent> raw = make(//
        new RawEvent(RawEventType.ADD, 1, "/file1.txt", "0"));
        List<StructuredEvent> result = translator.parseMessages(raw);
        check(result, //
            new CreationEvent(1, "/file1.txt", "0"));
    }

    @Test
    public void testFileMultipleAdd()
    {
        List<RawEvent> raw = make(//
            new RawEvent(RawEventType.ADD, 1, "/file1.txt", "0"),
            new RawEvent(RawEventType.ADD, 2, "/file2.txt", "1"));
        List<StructuredEvent> result = translator.parseMessages(raw);
        check(result, //
            new CreationEvent(1, "/file1.txt", "0"),
            new CreationEvent(2, "/file2.txt", "1"));
    }

    @Test
    public void testFileDelete()
    {
        List<RawEvent> raw = make(//
        new RawEvent(RawEventType.DEL, 1, "/file1.txt", "0"));
        List<StructuredEvent> result = translator.parseMessages(raw);
        check(result, //
            new FileDeletionEvent(1, "/file1.txt", "0"));
    }

    @Test
    public void testFileMultipleDelete()
    {
        List<RawEvent> raw = make(//
            new RawEvent(RawEventType.DEL, 1, "/file1.txt", "0"),
            new RawEvent(RawEventType.DEL, 2, "/file2.txt", FileSystemData.DIRECTORY_HASH));
        List<StructuredEvent> result = translator.parseMessages(raw);
        check(result, //
            new FileDeletionEvent(1, "/file1.txt", "0"),
            new DirectoryDeletionEvent(2, "/file2.txt"));
    }

    @Test
    public void testDeleteFolder()
    {
        List<RawEvent> raw = make(//
            new RawEvent(RawEventType.DEL, 1, "/file1.txt", "0"),
            new RawEvent(RawEventType.DEL, 1, "/file1.txt", "0"),
            new RawEvent(RawEventType.DEL, 1, "/file1.txt", "0"),
            new RawEvent(RawEventType.DEL, 2, "/file2.txt", FileSystemData.DIRECTORY_HASH));
        List<StructuredEvent> result = translator.parseMessages(raw);
        check(result,//
            new FileDeletionEvent(1, "/file1.txt", "0"),
            new DirectoryDeletionEvent(2, "/file2.txt"));
    }

    private static List<RawEvent> make(RawEvent... events)
    {
        return Arrays.asList(events);
    }

    private static void check(Collection<StructuredEvent> result, StructuredEvent... expectedEvents)
    {
        Assert.assertEquals(result.size(), expectedEvents.length);
        Iterator<StructuredEvent> iter = result.iterator();
        for (StructuredEvent expected : expectedEvents)
        {
            StructuredEvent actual = iter.next();
            Assert.assertEquals(actual, expected);
        }
    }

}
