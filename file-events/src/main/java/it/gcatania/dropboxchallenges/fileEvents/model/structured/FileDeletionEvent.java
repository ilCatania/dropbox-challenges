package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public class FileDeletionEvent extends DeletionEvent implements FileEvent
{

    /**
     * the deleted data
     */
    public final FileData data;

    public FileDeletionEvent(RawEvent ev)
    {
        super(ev);
        data = (FileData) super.data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        // data equality already checked by StructuredEvent.equals()
        return obj instanceof FileDeletionEvent && super.equals(obj);
    }
}