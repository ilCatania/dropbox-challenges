package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public class FileDeletionEvent extends DeletionEvent implements FileEvent
{

    private final FileData deletedData;

    public FileDeletionEvent(RawEvent ev)
    {
        deletedData = (FileData) ev.data;
    }

    @Override
    public FileData getData()
    {
        return deletedData;
    }

}
