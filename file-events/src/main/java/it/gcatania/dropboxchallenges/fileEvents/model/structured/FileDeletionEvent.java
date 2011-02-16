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

    /**
     * the deleted data
     */
    public final FileData data;

    public FileDeletionEvent(RawEvent ev)
    {
        super(ev);
        data = (FileData) super.data;
    }

    public FileDeletionEvent(long timeStamp, String path, String hash)
    {
        super(timeStamp, path, hash);
        data = (FileData) super.data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display(DateFormat df)
    {
        return fmt(FMT, tsFmt(df), data.name, data.parentFolder);
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