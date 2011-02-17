package it.gcatania.dropboxchallenges.fileEvents.model.structured;

/**
 * @author gcatania
 */
public abstract class CreationEvent extends StructuredEvent
{

    public CreationEvent(long timeStamp)
    {
        super(timeStamp);
    }

}
