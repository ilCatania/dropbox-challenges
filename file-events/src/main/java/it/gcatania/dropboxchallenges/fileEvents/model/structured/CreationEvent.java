package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public class CreationEvent extends StructuredEvent
{

    public CreationEvent(RawEvent delEvent)
    {
        super(delEvent.timeStamp, delEvent.data);
    }

    public CreationEvent(long timeStamp, String path, String hash)
    {
        super(timeStamp, path, hash);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        // data equality already checked by StructuredEvent.equals()
        return obj instanceof CreationEvent && super.equals(obj);
    }

}
