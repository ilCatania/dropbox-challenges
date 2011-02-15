package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileSystemData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public class CreationEvent extends StructuredEvent
{

    public CreationEvent(long timeStamp, String path, String hash)
    {
        super(timeStamp, FileSystemData.from(path, hash));
    }

    public CreationEvent(RawEvent delEvent)
    {
        super(delEvent.timeStamp, delEvent.data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof CreationEvent)
        {
            CreationEvent other = (CreationEvent) obj;
            return other.data.equals(data);
        }
        return false;
    }
}
