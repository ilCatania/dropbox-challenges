package it.gcatania.dropboxchallenges.fileEvents.model;

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
