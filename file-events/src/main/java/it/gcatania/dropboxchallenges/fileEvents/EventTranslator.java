package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author gcatania
 */
public class EventTranslator
{

    /*
     * possible events: file/folder created, file/folder deleted, file modified, file moved, file inside folder moved
     */

    public List<String> parseMessages(List<RawEvent> events)
    {

        List<RawEvent> eventCache = new ArrayList<RawEvent>();

        List<String> output = new ArrayList<String>();

        Iterator<RawEvent> eventIter = events.iterator();
        RawEvent lastEvent = eventIter.next();

        while (eventIter.hasNext())
        {
            RawEvent ev = eventIter.next();
            switch (ev.type)
            {

            }
        }

        return null;
    }

}
