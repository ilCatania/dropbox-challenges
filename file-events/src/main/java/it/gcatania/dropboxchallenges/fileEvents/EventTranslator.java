package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.CreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;
import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryDeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryMoveEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.FileContentChangeEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.FileDeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.FileMoveEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEventType;
import it.gcatania.dropboxchallenges.fileEvents.model.StructuredEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author gcatania
 */
public class EventTranslator
{

    public List<StructuredEvent> parseMessages(List<RawEvent> events)
    {

        List<StructuredEvent> output = new ArrayList<StructuredEvent>();

        Iterator<RawEvent> eventIter = events.iterator();
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
                    if (ev.data.isContainedIn(delEv.fullPath))
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
                        // next must be the corresponding add because of FE4: skip it
                        eventIter.next();
                        moveEv.addMove(ev.data);
                        continue;
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
                    if (addEvData.fullPath.equals(delEv.fullPath) && !addEvData.hash.equals(delEv.deletedData.hash))
                    {
                        lastEv = new FileContentChangeEvent(ev, ev); // TODO change constructor
                        continue;
                    }
                    else if (addEvData.hash.equals(delEv.deletedData.hash)
                        && addEvData.name.equals(delEv.deletedData.name)) // FE4
                    {
                        lastEv = new FileMoveEvent(ev, ev); // TODO change constructor
                        continue;
                    }
                }
                else if (lastEv instanceof DirectoryDeletionEvent && ev.data instanceof DirectoryData)
                {
                    DirectoryDeletionEvent delEv = (DirectoryDeletionEvent) lastEv;
                    DirectoryData addEvData = (DirectoryData) ev.data;
                    if (addEvData.name.equals(delEv.deletedData.name) && !addEvData.fullPath.equals(delEv.fullPath)) // FE4
                    {
                        lastEv = new DirectoryMoveEvent(ev, ev);
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
