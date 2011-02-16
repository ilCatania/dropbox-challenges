package it.gcatania.dropboxchallenges.fileEvents;

import static it.gcatania.dropboxchallenges.fileEvents.model.RawEventType.ADD;
import static it.gcatania.dropboxchallenges.fileEvents.model.RawEventType.DEL;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryCreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryDeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryMoveEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileContentChangeEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileCreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileDeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileMoveEvent;
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
        new RawEvent(ADD, 1, "/file1.txt", "0"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result, //
            new FileCreationEvent(1, "/file1.txt", "0"));
    }

    @Test
    public void testFileMultipleAdd()
    {
        List<RawEvent> raw = make(//
            new RawEvent(ADD, 1, "/file1.txt", "0"),
            new RawEvent(ADD, 2, "/file2.txt", "1"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result, //
            new FileCreationEvent(1, "/file1.txt", "0"),
            new FileCreationEvent(2, "/file2.txt", "1"));
    }

    @Test
    public void testFileDelete()
    {
        List<RawEvent> raw = make(//
        new RawEvent(DEL, 1, "/file1.txt", "0"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result, //
            new FileDeletionEvent(1, "/file1.txt", "0"));
    }

    @Test
    public void testFileMultipleDelete()
    {
        List<RawEvent> raw = make(//
            new RawEvent(DEL, 1, "/file1.txt", "0"),
            new RawEvent(DEL, 2, "/file2.txt"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result, //
            new FileDeletionEvent(1, "/file1.txt", "0"),
            new DirectoryDeletionEvent(2, "/file2.txt"));
    }

    @Test
    public void testDeleteFolder()
    {
        List<RawEvent> raw = make(//
            new RawEvent(ADD, 1, "/A"),
            new RawEvent(ADD, 2, "/A/B"),
            new RawEvent(ADD, 3, "/C"),
            new RawEvent(ADD, 4, "/X.txt", "0"),
            new RawEvent(ADD, 5, "/A/Y.txt"),
            new RawEvent(ADD, 6, "/A/Z.txt", "0"),
            new RawEvent(ADD, 7, "/W.txt", "0"),
            new RawEvent(DEL, 8, "/A"),
            new RawEvent(DEL, 9, "/A/B"),
            new RawEvent(DEL, 11, "/A/Y.txt", "0"),
            new RawEvent(DEL, 12, "/A/Z.txt", "0"),
            new RawEvent(DEL, 10, "/C"),
            new RawEvent(DEL, 14, "/X.txt", "0"));
        List<StructuredEvent> result = translator.translate(raw);
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
            new RawEvent(ADD, 7, "/W.txt", "0"),
            new RawEvent(DEL, 8, "/D/E/file.txt", "0"),
            new RawEvent(ADD, 9, "/D/E/file.txt", "1"),
            new RawEvent(DEL, 14, "/X.txt", "0"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result,//
            new FileCreationEvent(7, "/W.txt", "0"),
            new FileContentChangeEvent(9, "/D/E/file.txt", "1"),
            new FileDeletionEvent(14, "/X.txt", "0"));
    }

    @Test
    public void testFileMove()
    {
        List<RawEvent> raw = make(//
            new RawEvent(ADD, 7, "/W.txt", "0"),
            new RawEvent(DEL, 8, "/D/E/file.txt", "33"),
            new RawEvent(ADD, 9, "/file.txt", "33"),
            new RawEvent(DEL, 14, "/X.txt", "0"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result,//
            new FileCreationEvent(7, "/W.txt", "0"),
            new FileMoveEvent(9, "/D/E/file.txt", "/file.txt", "33"),
            new FileDeletionEvent(14, "/X.txt", "0"));
    }

    @Test
    public void testDirectoryMove()
    {
        List<RawEvent> raw = make(//
            new RawEvent(ADD, 7, "/W.txt", "0"),
            new RawEvent(DEL, 8, "/A/B/C/D"),
            new RawEvent(ADD, 8, "/A/D"),
            new RawEvent(DEL, 8, "/A/B/C/D/E"),
            new RawEvent(ADD, 8, "/A/D/E"),
            new RawEvent(DEL, 8, "/A/B/C/D/E/f1.txt", "f1hash"),
            new RawEvent(ADD, 8, "/A/D/E/f1.txt", "f1hash"),
            new RawEvent(DEL, 8, "/A/B/C/D/f2.txt", "f2hash"),
            new RawEvent(ADD, 8, "/A/D/f2.txt", "f2hash"),
            new RawEvent(DEL, 8, "/A/B/C/D/f3.txt", "f3hash"),
            new RawEvent(ADD, 9, "/A/D/f3.txt", "f3hashNew"),
            new RawEvent(DEL, 14, "/X.txt", "0"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result,//
            new FileCreationEvent(7, "/W.txt", "0"),
            new DirectoryMoveEvent(8, "/A/B/C/D", "/A/D", 2, 1),
            new FileDeletionEvent(8, "/A/B/C/D/f3.txt", "f3hash"),
            new FileCreationEvent(9, "/A/D/f3.txt", "f3hashNew"),
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
