package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public abstract class DeletionEvent extends StructuredEvent
{

    public DeletionEvent(RawEvent delEvent)
    {
        super(delEvent);
    }

    public DeletionEvent(long timeStamp, String filePath, String hash)
    {
        super(timeStamp, filePath, hash);
    }

    public DeletionEvent(long timeStamp, String directoryPath)
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
        return obj instanceof DeletionEvent && super.equals(obj);
    }

}
