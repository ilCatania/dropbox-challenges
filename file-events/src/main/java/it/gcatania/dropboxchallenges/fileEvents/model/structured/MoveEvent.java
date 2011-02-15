package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public abstract class MoveEvent extends StructuredEvent
{

    public final String fullPathFrom;

    public final String fullPathTo;

    public MoveEvent(RawEvent delEvent, RawEvent addEvent)
    {
        super(addEvent.timeStamp, addEvent.data);
        fullPathFrom = delEvent.data.fullPath;
        fullPathTo = addEvent.data.fullPath;
    }

    public MoveEvent(long timeStamp, String pathFrom, String pathTo, String hash)
    {
        super(timeStamp, pathTo, hash);
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