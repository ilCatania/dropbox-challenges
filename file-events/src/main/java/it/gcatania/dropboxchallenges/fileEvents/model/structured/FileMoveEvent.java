package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

/**
 * @author gcatania
 */
public class FileMoveEvent extends MoveEvent
{

    public FileMoveEvent(RawEvent delEvent, RawEvent addEvent)
    {
        super(delEvent, addEvent);
    }
}
