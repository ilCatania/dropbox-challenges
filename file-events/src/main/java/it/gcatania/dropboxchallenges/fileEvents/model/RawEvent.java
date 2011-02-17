package it.gcatania.dropboxchallenges.fileEvents.model;

/**
 * @author gcatania
 */
public class RawEvent
{

    public static String DIRECTORY_HASH = "-";

    public final long timeStamp;

    public final String path;

    public final String hash;

    public final boolean isDirectory;

    public final boolean isAdd;

    public final boolean isDel;

    /**
     * @param type the raw event type
     * @param timeStamp the event time stamp
     * @param path the event path
     */
    public RawEvent(RawEventType type, long timeStamp, String path, String hash)
    {
        this.timeStamp = timeStamp;
        this.path = path;
        this.hash = hash;
        isDirectory = hash.equals(DIRECTORY_HASH);
        isAdd = RawEventType.ADD.equals(type);
        isDel = !isAdd;
    }

    /**
     * shorthand constructor for unit testing
     * @param type the raw event type
     * @param timeStamp the event time stamp
     * @param path the event path
     */
    public RawEvent(RawEventType type, long timeStamp, String path)
    {
        this.timeStamp = timeStamp;
        this.path = path;
        hash = DIRECTORY_HASH = "-";
        isDirectory = true;
        isAdd = RawEventType.ADD.equals(type);
        isDel = !isAdd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return new StringBuilder()
            .append("RawEvent [timeStamp=")
            .append(timeStamp)
            .append(", path=")
            .append(path)
            .append(", hash=")
            .append(hash)
            .append(", isDirectory=")
            .append(isDirectory)
            .append(", isAdd=")
            .append(isAdd)
            .append("]")
            .toString();
    }

}
