package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public class FileMoveEvent extends MoveEvent implements FileEvent
{

    private final FileData targetData;

    public FileMoveEvent(RawEvent delEvent, RawEvent addEvent)
    {
        super(delEvent, addEvent);
        targetData = (FileData) addEvent.data;
    }

    @Override
    public FileData getData()
    {
        return targetData;
    }
}
