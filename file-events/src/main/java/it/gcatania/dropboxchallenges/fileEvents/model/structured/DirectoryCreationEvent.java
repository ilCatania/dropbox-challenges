package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;
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

    public final DirectoryData createdData;

    public DirectoryCreationEvent(long timeStamp, String path)
    {
        super(timeStamp);
        createdData = new DirectoryData(path);
    }

    public DirectoryCreationEvent(RawEvent ev)
    {
        this(ev.timeStamp, ev.path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display(DateFormat df)
    {
        return fmt(FMT, tsFmt(df), createdData.name, createdData.parentFolder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof DirectoryCreationEvent && super.equals(obj))
        {
            DirectoryCreationEvent other = (DirectoryCreationEvent) obj;
            return other.createdData.equals(createdData);
        }
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

}
