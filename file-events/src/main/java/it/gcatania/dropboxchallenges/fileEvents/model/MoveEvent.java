package it.gcatania.dropboxchallenges.fileEvents.model;

/**
 * @author gcatania
 */
public class MoveEvent extends StructuredEvent
{

    public final String fullPathFrom;

    public final String fullPathTo;

    public MoveEvent(RawEvent delEvent, RawEvent addEvent)
    {
        fullPathFrom = delEvent.data.fullPath;
        fullPathTo = addEvent.data.fullPath;
    }

}