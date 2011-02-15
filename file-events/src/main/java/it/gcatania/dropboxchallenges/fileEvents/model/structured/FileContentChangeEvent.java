package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public class FileContentChangeEvent extends StructuredEvent implements FileEvent
{

    public final FileData data;

    public FileContentChangeEvent(RawEvent delEvent, RawEvent addEvent)
    {
        super(addEvent.timeStamp, addEvent.data);
        data = (FileData) super.data;
    }

}
