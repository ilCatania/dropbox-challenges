package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

import java.text.DateFormat;
import java.text.MessageFormat;


/**
 * @author gcatania
 */
public class DirectoryCreationEvent extends CreationEvent
{

    // warning: not thread safe
    private static final MessageFormat FMT = new MessageFormat("{0}: directory {1} created in {2}.");

    public DirectoryCreationEvent(long timeStamp, String path)
    {
        super(timeStamp, path);
    }

    public DirectoryCreationEvent(RawEvent ev)
    {
        super(ev);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display(DateFormat df)
    {
        return fmt(FMT, tsFmt(df), data.name, data.parentFolder);
    }

}
