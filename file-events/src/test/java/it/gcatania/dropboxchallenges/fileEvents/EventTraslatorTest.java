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

import static it.gcatania.dropboxchallenges.fileEvents.model.RawEventType.ADD;
import static it.gcatania.dropboxchallenges.fileEvents.model.RawEventType.DEL;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryCreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryDeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryMoveEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryRenameEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileContentChangeEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileCreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileDeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileMoveEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileRenameEvent;
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
            new DirectoryDeletionEvent(2, "/file2.txt", 0, 0));
    }

    @Test
    public void testDirectoryDelete()
    {
        List<RawEvent> raw = make(//
            new RawEvent(ADD, 1, "/A"),
            new RawEvent(ADD, 2, "/A/B"),
            new RawEvent(ADD, 3, "/C"),
            new RawEvent(ADD, 4, "/X.txt", "0"),
            new RawEvent(ADD, 5, "/A/Y.txt", "0"),
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
            new DirectoryDeletionEvent(10, "/C", 0, 0),
            new FileDeletionEvent(14, "/X.txt", "0"));
    }

    @Test
    public void testFileContentChange()
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
    public void testFileAddAndDel()
    {
        List<RawEvent> raw = make(//
            new RawEvent(ADD, 1, "/asdf", "4"),
            new RawEvent(DEL, 1, "/asdf", "4"),
            new RawEvent(DEL, 1, "/asdf", "4"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result,//
            new FileCreationEvent(1, "/asdf", "4"),
            new FileDeletionEvent(1, "/asdf", "4"),
            new FileDeletionEvent(1, "/asdf", "4"));
    }

    @Test
    public void testNoFileContentChange()
    {
        List<RawEvent> raw = make(//
            new RawEvent(ADD, 7, "/W.txt", "0"),
            new RawEvent(DEL, 8, "/D/E/file.txt", "0"),
            new RawEvent(ADD, 9, "/D/E/file2.txt", "1"),
            new RawEvent(DEL, 14, "/X.txt", "0"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result,//
            new FileCreationEvent(7, "/W.txt", "0"),
            new FileDeletionEvent(8, "/D/E/file.txt", "0"),
            new FileCreationEvent(9, "/D/E/file2.txt", "1"),
            new FileDeletionEvent(14, "/X.txt", "0"));
    }

    @Test
    public void testFileMove1()
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
    public void testFileMove2()
    {
        List<RawEvent> raw = make(//
            new RawEvent(ADD, 7, "/W.txt", "0"),
            new RawEvent(DEL, 8, "/file.txt", "33"),
            new RawEvent(ADD, 9, "/D/E/file.txt", "33"),
            new RawEvent(DEL, 14, "/X.txt", "0"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result,//
            new FileCreationEvent(7, "/W.txt", "0"),
            new FileMoveEvent(9, "/file.txt", "/D/E/file.txt", "33"),
            new FileDeletionEvent(14, "/X.txt", "0"));
    }

    @Test
    public void testFileNotMove1()
    {
        List<RawEvent> raw = make(//
            new RawEvent(ADD, 7, "/W.txt", "0"),
            new RawEvent(DEL, 8, "/D/E/file.txt", "33"),
            new RawEvent(ADD, 9, "/file.txt", "35"), // different hash
            new RawEvent(DEL, 14, "/X.txt", "0"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result,//
            new FileCreationEvent(7, "/W.txt", "0"),
            new FileDeletionEvent(8, "/D/E/file.txt", "33"),
            new FileCreationEvent(9, "/file.txt", "35"),
            new FileDeletionEvent(14, "/X.txt", "0"));
    }

    @Test
    public void testFileNotMove2()
    {
        List<RawEvent> raw = make(//
            new RawEvent(ADD, 7, "/W.txt", "0"),
            new RawEvent(DEL, 8, "/D/E/file.txt", "33"),
            new RawEvent(ADD, 9, "/fileNew.txt", "33"), // different name
            new RawEvent(DEL, 14, "/X.txt", "0"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result,//
            new FileCreationEvent(7, "/W.txt", "0"),
            new FileDeletionEvent(8, "/D/E/file.txt", "33"),
            new FileCreationEvent(9, "/fileNew.txt", "33"),
            new FileDeletionEvent(14, "/X.txt", "0"));
    }

    @Test
    public void testFileRename()
    {
        List<RawEvent> raw = make(//
            new RawEvent(ADD, 7, "/W.txt", "0"),
            new RawEvent(DEL, 8, "/D/E/file.txt", "33"),
            new RawEvent(ADD, 9, "/D/E/fileNew.txt", "33"),
            new RawEvent(DEL, 14, "/X.txt", "0"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result,//
            new FileCreationEvent(7, "/W.txt", "0"),
            new FileRenameEvent(9, "/D/E/file.txt", "/D/E/fileNew.txt", "33"),
            new FileDeletionEvent(14, "/X.txt", "0"));
    }

    @Test
    public void testFileNotRename()
    {
        List<RawEvent> raw = make(//
            new RawEvent(ADD, 7, "/W.txt", "0"),
            new RawEvent(DEL, 8, "/D/E/file.txt", "33"),
            new RawEvent(ADD, 9, "/D/E/fileNew.txt", "35"), // different hash
            new RawEvent(DEL, 14, "/X.txt", "0"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result,//
            new FileCreationEvent(7, "/W.txt", "0"),
            new FileDeletionEvent(8, "/D/E/file.txt", "33"),
            new FileCreationEvent(9, "/D/E/fileNew.txt", "35"),
            new FileDeletionEvent(14, "/X.txt", "0"));
    }

    @Test
    public void testDirectoryMove()
    {
        List<RawEvent> raw = make(//
            new RawEvent(ADD, 7, "/W.txt", "0"),
            new RawEvent(DEL, 8, "/A/B/C/D"),
            new RawEvent(DEL, 8, "/A/B/C/D/E"),
            new RawEvent(DEL, 8, "/A/B/C/D/E/f1.txt", "f1hash"),
            new RawEvent(DEL, 8, "/A/B/C/D/f2.txt", "f2hash"),
            new RawEvent(ADD, 8, "/A/D"),
            new RawEvent(ADD, 8, "/A/D/E"),
            new RawEvent(ADD, 8, "/A/D/E/f1.txt", "f1hash"),
            new RawEvent(ADD, 8, "/A/D/f2.txt", "f2hash"),
            new RawEvent(ADD, 9, "/A/D/f3.txt", "f3hash"),
            new RawEvent(DEL, 10, "/A/B/C/D/f3.txt", "f3hashNew"),
            new RawEvent(DEL, 14, "/X.txt", "0"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result,//
            new FileCreationEvent(7, "/W.txt", "0"),
            new DirectoryMoveEvent(8, "/A/B/C/D", "/A/D", 2, 1),
            new FileCreationEvent(9, "/A/D/f3.txt", "f3hash"),
            new FileDeletionEvent(10, "/A/B/C/D/f3.txt", "f3hashNew"),
            new FileDeletionEvent(14, "/X.txt", "0"));
    }

    @Test
    public void testDirectoryNotMove1()
    {
        List<RawEvent> raw = make(//
            new RawEvent(ADD, 7, "/W.txt", "0"),
            new RawEvent(DEL, 8, "/A/B/C/D"),
            new RawEvent(DEL, 8, "/A/B/C/D/E"),
            new RawEvent(DEL, 8, "/A/B/C/D/E/f1.txt", "f1hash"),
            new RawEvent(DEL, 8, "/A/B/C/D/f2.txt", "f2hash"),
            new RawEvent(ADD, 8, "/A/D"),
            new RawEvent(ADD, 8, "/A/D/E"),
            // different hash on f1.txt should prevent identification as directory move
            new RawEvent(ADD, 8, "/A/D/E/f1.txt", "f1hashNew"),
            new RawEvent(ADD, 8, "/A/D/f2.txt", "f2hash"),
            new RawEvent(ADD, 9, "/A/D/f3.txt", "f3hash"),
            new RawEvent(DEL, 10, "/A/B/C/D/f3.txt", "f3hashNew"),
            new RawEvent(DEL, 14, "/X.txt", "0"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result,//
            new FileCreationEvent(7, "/W.txt", "0"),
            new DirectoryDeletionEvent(8, "/A/B/C/D", 2, 1),
            new DirectoryCreationEvent(8, "/A/D"),
            new DirectoryCreationEvent(8, "/A/D/E"),
            new FileCreationEvent(8, "/A/D/E/f1.txt", "f1hashNew"),
            new FileCreationEvent(8, "/A/D/f2.txt", "f2hash"),
            new FileCreationEvent(9, "/A/D/f3.txt", "f3hash"),
            new FileDeletionEvent(10, "/A/B/C/D/f3.txt", "f3hashNew"),
            new FileDeletionEvent(14, "/X.txt", "0"));
    }

    @Test
    public void testDirectoryNotMove2()
    {
        List<RawEvent> raw = make(//
            new RawEvent(ADD, 7, "/W.txt", "0"),
            new RawEvent(DEL, 8, "/A/B/C/D"),
            new RawEvent(DEL, 8, "/A/B/C/D/E"),
            new RawEvent(DEL, 8, "/A/B/C/D/E/f1.txt", "f1hash"),
            new RawEvent(DEL, 8, "/A/B/C/D/f2.txt", "f2hash"),
            new RawEvent(ADD, 8, "/A/D"),
            new RawEvent(ADD, 8, "/A/D/E"),
            new RawEvent(ADD, 8, "/A/D/E/f1.txt", "f1hash"),
            // missing add on f2.txt should precent identification as directory move
            // new RawEvent(ADD, 8, "/A/D/f2.txt", "f2hash"),
            new RawEvent(ADD, 9, "/A/D/f3.txt", "f3hash"),
            new RawEvent(DEL, 10, "/A/B/C/D/f3.txt", "f3hashNew"),
            new RawEvent(DEL, 14, "/X.txt", "0"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result,//
            new FileCreationEvent(7, "/W.txt", "0"),
            new DirectoryDeletionEvent(8, "/A/B/C/D", 2, 1),
            new DirectoryCreationEvent(8, "/A/D"),
            new DirectoryCreationEvent(8, "/A/D/E"),
            new FileCreationEvent(8, "/A/D/E/f1.txt", "f1hash"),
            new FileCreationEvent(9, "/A/D/f3.txt", "f3hash"),
            new FileDeletionEvent(10, "/A/B/C/D/f3.txt", "f3hashNew"),
            new FileDeletionEvent(14, "/X.txt", "0"));
    }

    @Test
    public void testDirectoryRename()
    {
        List<RawEvent> raw = make(//
            new RawEvent(ADD, 7, "/W.txt", "0"),
            new RawEvent(DEL, 8, "/A/B/C/D"),
            new RawEvent(DEL, 8, "/A/B/C/D/E"),
            new RawEvent(DEL, 8, "/A/B/C/D/E/f1.txt", "f1hash"),
            new RawEvent(DEL, 8, "/A/B/C/D/f2.txt", "f2hash"),
            new RawEvent(ADD, 8, "/A/B/C/Dnew"),
            new RawEvent(ADD, 8, "/A/B/C/Dnew/E"),
            new RawEvent(ADD, 8, "/A/B/C/Dnew/E/f1.txt", "f1hash"),
            new RawEvent(ADD, 8, "/A/B/C/Dnew/f2.txt", "f2hash"),
            new RawEvent(ADD, 9, "/A/B/C/Dnew/f3.txt", "f3hash"),
            new RawEvent(DEL, 10, "/A/B/C/Dnew/f3.txt", "f3hashNew"),
            new RawEvent(DEL, 14, "/X.txt", "0"));
        List<StructuredEvent> result = translator.translate(raw);
        check(result,//
            new FileCreationEvent(7, "/W.txt", "0"),
            new DirectoryRenameEvent(8, "/A/B/C/D", "/A/B/C/Dnew"),
            new FileCreationEvent(9, "/A/B/C/Dnew/f3.txt", "f3hash"),
            new FileDeletionEvent(10, "/A/B/C/Dnew/f3.txt", "f3hashNew"),
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
