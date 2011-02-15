package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public class FileContentChangeEvent implements FileEvent
{

    private final FileData data;

    public FileContentChangeEvent(RawEvent delEvent, RawEvent addEvent)
    {
        data = (FileData) addEvent.data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileData getData()
    {
        return data;
    }

}
