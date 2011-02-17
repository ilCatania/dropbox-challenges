package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

import java.text.DateFormat;
import java.text.MessageFormat;


/**
 * @author gcatania
 */
public class FileDeletionEvent extends DeletionEvent implements FileEvent
{

    // warning: not thread safe
    private static final MessageFormat FMT = new MessageFormat("{0}: file {1} created in {2}.");

    public final FileData deletedData;

    public FileDeletionEvent(RawEvent ev)
    {
        this(ev.timeStamp, ev.path, ev.hash);
    }

    public FileDeletionEvent(long timeStamp, String path, String hash)
    {
        super(timeStamp);
        deletedData = new FileData(path, hash);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display(DateFormat df)
    {
        return fmt(FMT, tsFmt(df), deletedData.name, deletedData.parentFolder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        // data equality already checked by StructuredEvent.equals()
        if (obj instanceof FileDeletionEvent && super.equals(obj))
        {
            FileDeletionEvent other = (FileDeletionEvent) obj;
            return other.deletedData.equals(deletedData);
        }
        return false;
    }
}