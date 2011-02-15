package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

/**
 * @author gcatania
 */
public class FileDeletionEvent extends DeletionEvent
{

    public final FileData deletedData;

    public FileDeletionEvent(RawEvent ev)
    {
        super(ev);
        deletedData = ((FileData) ev.data);
    }

}
