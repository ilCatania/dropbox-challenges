package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.CascadingDirectoryEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryCreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileContentChangeEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileCreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileDeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileMoveEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.StructuredEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


/**
 * main engine class for event translation.
 * @author gcatania
 */
public class EventTranslator
{

    /**
     * translates a list of raw events into structured ones.
     * @param events the raw events
     * @return the structured ones
     */
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
                if (lastEv instanceof CascadingDirectoryEvent)
                {
                    CascadingDirectoryEvent cEv = (CascadingDirectoryEvent) lastEv;
                    if (cEv.addDeletion(ev))
                    {
                        continue;
                    }
                    else
                    {
                        output.addAll(cEv.getEvents());
                        lastEv = null;
                    }
                }
                if (lastEv != null)
                {
                    output.add(lastEv);
                }
                lastEv = ev.isDirectory ? new CascadingDirectoryEvent(ev) : new FileDeletionEvent(ev);
            }
            else
            {
                if (lastEv instanceof CascadingDirectoryEvent)
                {
                    CascadingDirectoryEvent cEv = (CascadingDirectoryEvent) lastEv;
                    if (cEv.addCreation(ev))
                    {
                        continue;
                    }
                    else
                    {
                        output.addAll(cEv.getEvents());
                        lastEv = null;
                    }
                }
                else if (lastEv instanceof FileDeletionEvent)
                {
                    FileDeletionEvent delEv = (FileDeletionEvent) lastEv;
                    if (ev.hash.equals(delEv.data.hash))
                    {
                        if (delEv.data.sameName(ev.path))
                        {
                            lastEv = new FileMoveEvent(ev.timeStamp, delEv.data.fullPath, ev.path, ev.hash);
                            continue;
                        }
                    }
                    else if (ev.path.equals(delEv.data.fullPath))
                    {
                        lastEv = new FileContentChangeEvent(ev);
                        continue;
                    }

                }
                if (lastEv != null)
                {
                    output.add(lastEv);
                }
                lastEv = ev.isDirectory ? new DirectoryCreationEvent(ev) : new FileCreationEvent(ev);
            }
        }

        if (lastEv instanceof CascadingDirectoryEvent)
        {
            CascadingDirectoryEvent cEv = (CascadingDirectoryEvent) lastEv;
            output.addAll(cEv.getEvents());
        }
        else if (lastEv != null)
        {
            output.add(lastEv);
        }
        return output;
    }
}
