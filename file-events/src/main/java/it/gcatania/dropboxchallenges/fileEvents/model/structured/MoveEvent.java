package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;
import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.FileSystemData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public abstract class MoveEvent extends StructuredEvent
{

    public final FileSystemData fromData;

    public final String fullPathFrom;

    public final String fullPathTo;

    public MoveEvent(RawEvent delEvent, RawEvent addEvent)
    {
        super(addEvent);
        fromData = delEvent.isDirectory ? new DirectoryData(delEvent.path) : new FileData(delEvent.path, delEvent.hash);
        fullPathFrom = delEvent.path;
        fullPathTo = addEvent.path;
    }

    public MoveEvent(long timeStamp, String pathFrom, String pathTo, String hash)
    {
        super(timeStamp, pathTo, hash);
        fromData = new FileData(pathFrom, hash);
        fullPathFrom = pathFrom;
        fullPathTo = pathTo;
    }

    public MoveEvent(long timeStamp, String pathFrom, String pathTo)
    {
        super(timeStamp, pathTo);
        fromData = new DirectoryData(pathFrom);
        fullPathFrom = pathFrom;
        fullPathTo = pathTo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof MoveEvent && super.equals(obj))
        {
            MoveEvent other = (MoveEvent) obj;
            return other.fullPathFrom.equals(fullPathFrom) && other.fullPathTo.equals(fullPathTo);
        }
        return false;
    }
}