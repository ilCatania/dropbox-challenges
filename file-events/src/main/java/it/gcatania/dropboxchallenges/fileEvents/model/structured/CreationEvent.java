package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileSystemData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public class CreationEvent implements StructuredEvent
{

    private final FileSystemData data;

    public CreationEvent(String path, String hash)
    {
        data = FileSystemData.from(path, hash);
    }

    public CreationEvent(RawEvent ev)
    {
        data = ev.data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileSystemData getData()
    {
        return data;
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
