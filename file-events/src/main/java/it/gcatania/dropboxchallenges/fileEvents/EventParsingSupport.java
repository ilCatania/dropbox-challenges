package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.common.ParsingSupport;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEventType;


/**
 * helper class for parsing raw events.
 * @author gcatania
 */
public class EventParsingSupport extends ParsingSupport<RawEvent>
{

    public EventParsingSupport()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RawEvent getObject(String objectLine) throws IllegalArgumentException
    {
        String[] eventData = objectLine.split(" ");
        if (eventData.length != 4)
        {
            throw new IllegalArgumentException("Cannot read event data from: " + objectLine);
        }

        RawEventType eventType = RawEventType.valueOf(eventData[0]);
        long timeStamp = Long.parseLong(eventData[1]);
        String path = eventData[2];
        String hash = eventData[3];
        return new RawEvent(eventType, timeStamp, path, hash);
    }
}
