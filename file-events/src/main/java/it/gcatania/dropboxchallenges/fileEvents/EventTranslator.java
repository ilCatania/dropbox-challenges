package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;
import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEventType;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.CreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryDeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryMoveEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileContentChangeEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileDeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileMoveEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.StructuredEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;


/**
 * @author gcatania
 */
public class EventTranslator
{

    public List<StructuredEvent> parseMessages(List<RawEvent> events)
    {

        List<StructuredEvent> output = new ArrayList<StructuredEvent>();

        ListIterator<RawEvent> eventIter = events.listIterator();
        StructuredEvent lastEv = null;

        while (eventIter.hasNext())
        {
            RawEvent ev = eventIter.next();
            if (ev.type.equals(RawEventType.ADD))
            {
                output.add(new CreationEvent(ev));
                continue;
            }
            lastEv = ev.data instanceof FileData ? new FileDeletionEvent(ev) : new DirectoryDeletionEvent(ev);
            break;
        }

        while (eventIter.hasNext())
        {
            RawEvent ev = eventIter.next();
            if (ev.type.equals(RawEventType.DEL))
            {
                // 1) check if folder/file has been deleted due to a deleted parent folder
                if (lastEv instanceof DirectoryDeletionEvent)
                {
                    DirectoryDeletionEvent delEv = (DirectoryDeletionEvent) lastEv;
                    if (ev.data.isContainedIn(delEv.data.fullPath))
                    {
                        delEv.addDeletion(ev.data);
                        continue;
                    }
                }
                else if (lastEv instanceof DirectoryMoveEvent)
                {
                    // 2) otherwise check if it's a move
                    DirectoryMoveEvent moveEv = (DirectoryMoveEvent) lastEv;
                    if (ev.data.isContainedIn(moveEv.fullPathFrom))
                    {
                        if (eventIter.hasNext())
                        {
                            RawEvent next = eventIter.next();
                            if (next.type.equals(RawEventType.ADD)
                                && Pattern
                                    .compile(moveEv.fullPathFrom)
                                    .matcher(ev.data.fullPath)
                                    .replaceFirst(moveEv.fullPathTo)
                                    .equals(next.data.fullPath))
                            {
                                moveEv.addMove(ev.data);
                                continue;
                            }
                            eventIter.previous();
                        }
                    }
                }

                if (lastEv != null)
                {
                    output.add(lastEv);
                }

                if (ev.data instanceof FileData)
                {
                    lastEv = new FileDeletionEvent(ev);
                }
                else
                {
                    lastEv = new DirectoryDeletionEvent(ev);
                }
            }
            else
            {
                if (lastEv instanceof FileDeletionEvent && ev.data instanceof FileData)
                {
                    FileDeletionEvent delEv = (FileDeletionEvent) lastEv;
                    FileData addEvData = (FileData) ev.data;
                    if (addEvData.fullPath.equals(delEv.data.fullPath) && !addEvData.hash.equals(delEv.data.hash))
                    {
                        lastEv = new FileContentChangeEvent(ev);
                        continue;
                    }
                    else if (addEvData.hash.equals(delEv.data.hash) && addEvData.name.equals(delEv.data.name)) // FE4
                    {
                        lastEv = new FileMoveEvent(
                            ev.timeStamp,
                            delEv.data.fullPath,
                            ev.data.fullPath,
                            ((FileData) ev.data).hash);
                        continue;
                    }
                }
                else if (lastEv instanceof DirectoryDeletionEvent && ev.data instanceof DirectoryData)
                {
                    DirectoryDeletionEvent delEv = (DirectoryDeletionEvent) lastEv;
                    DirectoryData addEvData = (DirectoryData) ev.data;
                    if (addEvData.name.equals(delEv.data.name) && !addEvData.fullPath.equals(delEv.data.fullPath)) // FE4
                    {
                        lastEv = new DirectoryMoveEvent(ev.timeStamp, delEv.data.fullPath, ev.data.fullPath);
                        continue;
                    }
                }
                if (lastEv != null)
                {
                    output.add(lastEv);
                }
                lastEv = new CreationEvent(ev);
            }
        }
        if (lastEv != null)
        {
            output.add(lastEv);
        }
        return output;
    }
}
