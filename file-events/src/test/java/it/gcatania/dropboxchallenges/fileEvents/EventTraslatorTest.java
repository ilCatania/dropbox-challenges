package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.FileSystemData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEventType;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryCreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryDeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileContentChangeEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileCreationEvent;
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
            new FileCreationEvent(1, "/file1.txt", "0"));
    }

    @Test
    public void testFileMultipleAdd()
    {
        List<RawEvent> raw = make(//
            new RawEvent(RawEventType.ADD, 1, "/file1.txt", "0"),
            new RawEvent(RawEventType.ADD, 2, "/file2.txt", "1"));
        List<StructuredEvent> result = translator.parseMessages(raw);
        check(result, //
            new FileCreationEvent(1, "/file1.txt", "0"),
            new FileCreationEvent(2, "/file2.txt", "1"));
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
            new RawEvent(RawEventType.ADD, 1, "/A", FileSystemData.DIRECTORY_HASH),
            new RawEvent(RawEventType.ADD, 2, "/A/B", FileSystemData.DIRECTORY_HASH),
            new RawEvent(RawEventType.ADD, 3, "/C", FileSystemData.DIRECTORY_HASH),
            new RawEvent(RawEventType.ADD, 4, "/X.txt", "0"),
            new RawEvent(RawEventType.ADD, 5, "/A/Y.txt", FileSystemData.DIRECTORY_HASH),
            new RawEvent(RawEventType.ADD, 6, "/A/Z.txt", "0"),
            new RawEvent(RawEventType.ADD, 7, "/W.txt", "0"),
            new RawEvent(RawEventType.DEL, 8, "/A", FileSystemData.DIRECTORY_HASH),
            new RawEvent(RawEventType.DEL, 9, "/A/B", FileSystemData.DIRECTORY_HASH),
            new RawEvent(RawEventType.DEL, 11, "/A/Y.txt", "0"),
            new RawEvent(RawEventType.DEL, 12, "/A/Z.txt", "0"),
            new RawEvent(RawEventType.DEL, 10, "/C", FileSystemData.DIRECTORY_HASH),
            new RawEvent(RawEventType.DEL, 14, "/X.txt", "0"));
        List<StructuredEvent> result = translator.parseMessages(raw);
        check(result,//
            new DirectoryCreationEvent(1, "/A"),
            new DirectoryCreationEvent(2, "/A/B"),
            new DirectoryCreationEvent(3, "/C"),
            new FileCreationEvent(4, "/X.txt", "0"),
            new FileCreationEvent(5, "/A/Y.txt", "0"),
            new FileCreationEvent(6, "/A/Z.txt", "0"),
            new FileCreationEvent(7, "/W.txt", "0"),
            new DirectoryDeletionEvent(8, "/A", 2, 1),
            new DirectoryDeletionEvent(10, "/C"),
            new FileDeletionEvent(14, "/X.txt", "0"));
    }

    @Test
    public void testChangeFileContent()
    {
        List<RawEvent> raw = make(//
            new RawEvent(RawEventType.ADD, 7, "/W.txt", "0"),
            new RawEvent(RawEventType.DEL, 8, "/D/E/file.txt", "0"),
            new RawEvent(RawEventType.ADD, 9, "/D/E/file.txt", "1"),
            new RawEvent(RawEventType.DEL, 14, "/X.txt", "0"));
        List<StructuredEvent> result = translator.parseMessages(raw);
        check(result,//
            new FileCreationEvent(7, "/W.txt", "0"),
            new FileContentChangeEvent(9, "/D/E/file.txt", "1"),
            new FileDeletionEvent(14, "/X.txt", "0"));
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
