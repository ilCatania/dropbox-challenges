package it.gcatania.dropboxchallenges.fileEvents.model.structured;

/**
 * @author gcatania
 */
public abstract class DeletionEvent extends StructuredEvent
{

    public DeletionEvent(long timeStamp)
    {
        super(timeStamp);
    }

}
