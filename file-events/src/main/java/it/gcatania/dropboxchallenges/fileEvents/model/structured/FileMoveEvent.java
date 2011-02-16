package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public class FileMoveEvent extends MoveEvent implements FileEvent
{

    public final FileData fromData;

    public final FileData data;

    public FileMoveEvent(RawEvent delEvent, RawEvent addEvent)
    {
        super(delEvent, addEvent);
        data = (FileData) super.data;
        fromData = (FileData) super.fromData;
    }

    public FileMoveEvent(long timeStamp, String pathFrom, String pathTo, String hash)
    {
        super(timeStamp, pathFrom, pathTo, hash);
        data = (FileData) super.data;
        fromData = (FileData) super.fromData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        // data equality already checked by StructuredEvent.equals()
        return obj instanceof FileMoveEvent && super.equals(obj);
    }
}
