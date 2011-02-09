package it.gcatania.dropboxchallenges.fileEvents.model;

/**
 * @author gcatania
 */
public class RawEvent
{

    public final RawEventType type;

    public final long timeStamp;

    public final String path;

    public final String hash;

    /**
     * @param type the raw event type
     * @param timeStamp the event time stamp
     * @param path the event path
     */
    public RawEvent(RawEventType type, long timeStamp, String path, String hash)
    {
        this.type = type;
        this.timeStamp = timeStamp;
        this.path = path;
        this.hash = hash;
    }

}
