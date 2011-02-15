package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileSystemData;


/**
 * @author gcatania
 */
public abstract class StructuredEvent
{

    public final long timeStamp;

    public final FileSystemData data;

    public StructuredEvent(long timeStamp, FileSystemData data)
    {
        this.timeStamp = timeStamp;
        this.data = data;
    }

    public StructuredEvent(long timeStamp, String path, String hash)
    {
        this.timeStamp = timeStamp;
        data = FileSystemData.from(path, hash);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        int hc = 1;
        hc += new Long(timeStamp).hashCode() * 3;
        hc += data.hashCode() * 7;
        return hc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof StructuredEvent)
        {
            StructuredEvent other = (StructuredEvent) obj;
            return other.timeStamp == timeStamp && other.data.equals(data);
        }
        return false;
    }

}
