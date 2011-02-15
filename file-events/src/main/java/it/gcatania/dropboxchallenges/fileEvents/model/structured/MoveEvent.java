package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public abstract class MoveEvent implements StructuredEvent
{

    public final String fullPathFrom;

    public final String fullPathTo;

    public MoveEvent(RawEvent delEvent, RawEvent addEvent)
    {
        fullPathFrom = delEvent.data.fullPath;
        fullPathTo = addEvent.data.fullPath;
    }

}