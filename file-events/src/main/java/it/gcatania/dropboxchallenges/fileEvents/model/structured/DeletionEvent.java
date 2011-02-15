package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public abstract class DeletionEvent extends StructuredEvent
{

    public DeletionEvent(RawEvent delEvent)
    {
        super(delEvent.timeStamp, delEvent.data);
    }
}
