package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

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
