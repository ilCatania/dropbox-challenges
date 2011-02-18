package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

import java.text.DateFormat;
import java.text.MessageFormat;


/**
 * @author gcatania
 */
public class FileCreationEvent extends StructuredEvent
{

    // warning: not thread safe
    private static final MessageFormat FMT = new MessageFormat("{0}: file {1} created in {2}.");

    public final FileData data;

    public FileCreationEvent(long timeStamp, String path, String hash)
    {
        super(timeStamp);
        data = new FileData(path, hash);
    }

    public FileCreationEvent(RawEvent ev)
    {
        this(ev.timeStamp, ev.path, ev.hash);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display(DateFormat df)
    {
        return fmt(FMT, tsFmt(df), data.name, data.parentPath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof FileCreationEvent && super.equals(obj))
        {
            FileCreationEvent other = (FileCreationEvent) obj;
            return other.data.equals(data);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return new StringBuilder()
            .append("FileCreationEvent [data=")
            .append(data)
            .append(", timeStamp=")
            .append(timeStamp)
            .append("]")
            .toString();
    }
}
