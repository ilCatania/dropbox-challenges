package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;
import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.FileSystemData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public abstract class StructuredEvent
{

    public final long timeStamp;

    public final FileSystemData data;

    public StructuredEvent(RawEvent ev)
    {
        timeStamp = ev.timeStamp;
        data = ev.isDirectory ? new DirectoryData(ev.path) : new FileData(ev.path, ev.hash);
    }

    public StructuredEvent(long timeStamp, String filePath, String hash)
    {
        this.timeStamp = timeStamp;
        data = new FileData(filePath, hash);
    }

    public StructuredEvent(long timeStamp, String directoryPath)
    {
        this.timeStamp = timeStamp;
        data = new DirectoryData(directoryPath);
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
