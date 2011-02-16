package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public class FileContentChangeEvent extends StructuredEvent implements FileEvent
{

    public final FileData data;

    public FileContentChangeEvent(RawEvent addEvent)
    {
        super(addEvent);
        data = (FileData) super.data;
    }

    public FileContentChangeEvent(long timeStamp, String path, String hash)
    {
        super(timeStamp, path, hash);
        data = (FileData) super.data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        // data equality already checked by StructuredEvent.equals()
        return obj instanceof FileContentChangeEvent && super.equals(obj);
    }

}
