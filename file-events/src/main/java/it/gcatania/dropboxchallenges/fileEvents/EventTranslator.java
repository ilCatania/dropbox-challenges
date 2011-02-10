package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.CreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.DeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.FileContentChangeEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
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
                    output.add(new DeletionEvent());
                }
            }
            else
            // ev.type = add
            {
                if (lastEvent.path.samePathAs(ev.path))
                {
                    for (RawEvent cached : eventCache.subList(0, eventCache.size() - 1))
                    {
                        output.add(new CreationEvent());
                    }

                    if (lastEvent.path instanceof FileData && ev.path instanceof FileData)
                    {
                        output.add(new FileContentChangeEvent());
                        lastEvent = ev;
                        lastEventType = ev.type;
                        continue;
                    }
                    else
                    {
                        output.add(new DeletionEvent()); // delete previous
                    }
                }
                else
                {
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
