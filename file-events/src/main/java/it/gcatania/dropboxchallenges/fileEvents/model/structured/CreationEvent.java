package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

/**
 * @author gcatania
 */
public class CreationEvent extends StructuredEvent
{

    public CreationEvent()
    {
        // TODO
    }

    public CreationEvent(RawEvent ev)
    {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof CreationEvent)
        {
            CreationEvent other = (CreationEvent) obj;
            return other != null; // TODO
        }
        return false;
    }
}
