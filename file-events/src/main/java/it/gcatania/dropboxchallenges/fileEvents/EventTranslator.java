package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryCreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryDeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryMoveEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileContentChangeEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileCreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileDeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileMoveEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.StructuredEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


/**
 * @author gcatania
 */
public class EventTranslator
{

    public List<StructuredEvent> translate(List<RawEvent> events)
    {

        List<StructuredEvent> output = new ArrayList<StructuredEvent>();

        ListIterator<RawEvent> eventIter = events.listIterator();
        StructuredEvent lastEv = null;

        while (eventIter.hasNext())
        {
            RawEvent ev = eventIter.next();
            if (ev.isDel)
            {
                // 1) check if folder/file has been deleted due to a deleted parent folder
                if (lastEv instanceof DirectoryDeletionEvent)
                {
                    DirectoryDeletionEvent delEv = (DirectoryDeletionEvent) lastEv;
                    if (delEv.data.contains(ev.path))
                    {
                        delEv.addDeletion(ev.isDirectory);
                        continue;
                    }
                }
                else if (lastEv instanceof DirectoryMoveEvent)
                {
                    // 2) otherwise check if it's a move
                    DirectoryMoveEvent moveEv = (DirectoryMoveEvent) lastEv;
                    if (moveEv.fromData.contains(ev.path) && eventIter.hasNext())
                    {
                        RawEvent next = eventIter.next();
                        if (next.isAdd && moveEv.isChildMove(ev, next))
                        {
                            moveEv.addMove(ev.isDirectory);
                            continue;
                        }
                        eventIter.previous();
                    }
                }

                if (lastEv != null)
                {
                    output.add(lastEv);
                }

                lastEv = ev.isDirectory ? new DirectoryDeletionEvent(ev) : new FileDeletionEvent(ev);
            }
            else
            {
                if (ev.isDirectory)
                {
                    if (lastEv instanceof DirectoryDeletionEvent)
                    {
                        DirectoryDeletionEvent delEv = (DirectoryDeletionEvent) lastEv;
                        if (delEv.data.sameName(ev.path) && !ev.path.equals(delEv.data.fullPath)) // FE4
                        {
                            lastEv = new DirectoryMoveEvent(ev.timeStamp, delEv.data.fullPath, ev.path);
                            continue;
                        }
                    }
                }
                else
                {
                    if (lastEv instanceof FileDeletionEvent)
                    {
                        FileDeletionEvent delEv = (FileDeletionEvent) lastEv;
                        boolean sameHash = ev.hash.equals(delEv.data.hash);
                        if (sameHash)
                        {
                            if (delEv.data.sameName(ev.path))
                            {
                                lastEv = new FileMoveEvent(ev.timeStamp, delEv.data.fullPath, ev.path, ev.hash);
                                continue;
                            }
                        }
                        else
                        {
                            if (ev.path.equals(delEv.data.fullPath))
                            {
                                lastEv = new FileContentChangeEvent(ev);
                                continue;
                            }
                        }
                    }
                }
                if (lastEv != null)
                {
                    output.add(lastEv);
                }
                lastEv = ev.isDirectory ? new DirectoryCreationEvent(ev) : new FileCreationEvent(ev);
            }
        }
        if (lastEv != null)
        {
            output.add(lastEv);
        }
        return output;
    }
}
