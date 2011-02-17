package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

import java.text.DateFormat;
import java.text.MessageFormat;


/**
 * @author gcatania
 */
public class FileContentChangeEvent extends StructuredEvent
{

    // warning: not thread safe
    private static final MessageFormat FMT = new MessageFormat("{0}: file {1} in {2} modified.");

    public final FileData modifiedData;

    public FileContentChangeEvent(long timeStamp, String path, String hash)
    {
        super(timeStamp);
        modifiedData = new FileData(path, hash);
    }

    public FileContentChangeEvent(RawEvent addEv)
    {
        this(addEv.timeStamp, addEv.path, addEv.hash);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display(DateFormat df)
    {
        return fmt(FMT, tsFmt(df), modifiedData.name, modifiedData.parentFolder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof FileContentChangeEvent && super.equals(obj))
        {
            FileContentChangeEvent other = (FileContentChangeEvent) obj;
            return other.modifiedData.equals(modifiedData);
        }
        return false;
    }

}
