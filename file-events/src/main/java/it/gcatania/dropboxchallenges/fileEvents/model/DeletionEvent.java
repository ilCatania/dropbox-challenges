package it.gcatania.dropboxchallenges.fileEvents.model;

/**
 * @author gcatania
 */
public class DeletionEvent extends StructuredEvent
{

    public final String fullPath;

    public DeletionEvent(RawEvent ev)
    {
        super();
        fullPath = null;
    }

}
