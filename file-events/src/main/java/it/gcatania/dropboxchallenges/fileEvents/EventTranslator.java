package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.CreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.DeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.FileContentChangeEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
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

    public List<String> parseMessages(List<RawEvent> events)
    {

        List<RawEvent> eventCache = new ArrayList<RawEvent>();

        List<StructuredEvent> output = new ArrayList<StructuredEvent>();

        Iterator<RawEvent> eventIter = events.iterator();
        RawEvent lastEvent = eventIter.next();
        RawEventType lastEventType = lastEvent.type;
        RawEvent re = null;

        eventCache.add(lastEvent);
        while (eventIter.hasNext())
        {
            RawEvent ev = eventIter.next();
            if (ev.type.equals(lastEventType))
            {
                eventCache.add(ev);
                continue;
            }

            if (ev.type.equals(RawEventType.DEL))
            {
                for (RawEvent cached : eventCache)
                {
                    output.add(new CreationEvent(cached));
                }
                eventCache.clear();
                eventCache.add(ev);
                lastEvent = ev;
                lastEventType = ev.type;
            }
            else
            {
                // ev.type = add
                if (lastEvent.data.samePathAs(ev.data))
                {
                    for (RawEvent cached : eventCache.subList(0, eventCache.size() - 1))
                    {
                        output.add(new DeletionEvent(cached));
                    }

                    if (lastEvent.data instanceof FileData && ev.data instanceof FileData)
                    {
                        output.add(new FileContentChangeEvent(lastEvent, ev));
                    }
                    else
                    {
                        output.add(new DeletionEvent(lastEvent));
                        output.add(new CreationEvent(ev));
                    }
                    eventCache.clear();
                    eventCache.add(lastEvent);
                    lastEvent = ev;
                    lastEventType = ev.type;
                    continue;
                }
                else
                {
                    if (!lastEvent.data.sameType(ev.data))
                    {
                        // TODO gestire i precedenti eventi
                        output.add(new DeletionEvent(lastEvent));
                        output.add(new CreationEvent(ev));
                    }
                    else if (ev.data instanceof FileData)
                    {
                        FileData evData = (FileData) ev.data;
                        FileData lastEvData = (FileData) lastEvent.data;
                        if (evData.hash.equals(lastEvData.hash)) // FE4
                        {
                            output.add(new FileMoveEvent(lastEvent, ev));
                            eventCache.clear();
                            eventCache.add(lastEvent);
                            lastEvent = ev;
                            lastEventType = ev.type;
                            continue;
                        }
                        else
                        {
                            output.add(new DeletionEvent(lastEvent));
                            output.add(new CreationEvent(ev));
                        }

                    }
                    else
                    {
                        // directory moved

                    }

                    Iterator<RawEvent> iter = eventCache.iterator();
                    while (iter.hasNext())
                    {
                        RawEvent previouslyDeleted = iter.next();
                    }
                    // TODO
                }
            }

            eventCache.clear();
            eventCache.add(ev);
            lastEvent = ev;
            lastEventType = ev.type;
        }

        return null;
    }
}
