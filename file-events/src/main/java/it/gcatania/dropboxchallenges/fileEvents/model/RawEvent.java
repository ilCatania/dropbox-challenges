package it.gcatania.dropboxchallenges.fileEvents.model;

/**
 * @author gcatania
 */
public class RawEvent
{

    public static String DIRECTORY_HASH = "-";

    public final RawEventType type;

    public final long timeStamp;

    public final String path;

    public final String hash;

    public final boolean isDirectory;

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
        isDirectory = hash.equals(DIRECTORY_HASH);
    }

    /**
     * shorthand constructor for unit testing
     * @param type the raw event type
     * @param timeStamp the event time stamp
     * @param path the event path
     */
    public RawEvent(RawEventType type, long timeStamp, String path)
    {
        this.type = type;
        this.timeStamp = timeStamp;
        this.path = path;
        hash = DIRECTORY_HASH = "-";
        isDirectory = true;
    }

}
