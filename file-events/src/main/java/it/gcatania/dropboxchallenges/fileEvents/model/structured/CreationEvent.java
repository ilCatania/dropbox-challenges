package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public abstract class CreationEvent extends StructuredEvent
{

    public CreationEvent(RawEvent delEvent)
    {
        super(delEvent);
    }

    public CreationEvent(long timeStamp, String filePath, String hash)
    {
        super(timeStamp, filePath, hash);
    }

    public CreationEvent(long timeStamp, String directoryPath)
    {
        super(timeStamp, directoryPath);
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
