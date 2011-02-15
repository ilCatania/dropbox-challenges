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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        // data equality already checked by StructuredEvent.equals()
        return obj instanceof DeletionEvent && super.equals(obj);
    }

}
