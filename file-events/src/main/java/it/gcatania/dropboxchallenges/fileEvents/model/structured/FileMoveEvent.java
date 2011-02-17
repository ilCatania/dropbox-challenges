package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileData;

import java.text.DateFormat;
import java.text.MessageFormat;


/**
 * @author gcatania
 */
public class FileMoveEvent extends MoveEvent
{

    // warning: not thread safe
    private static final MessageFormat RENAME_FMT = new MessageFormat("{0}: file {0} in {1} renamed to {2}.");

    // warning: not thread safe
    private static final MessageFormat MOVE_FMT = new MessageFormat("{0}: file {0} moved from {1} to {2}.");

    public final FileData fromData;

    public final FileData toData;

    public FileMoveEvent(long timeStamp, String pathFrom, String pathTo, String hash)
    {
        super(timeStamp, pathFrom, pathTo);
        fromData = new FileData(pathFrom, hash);
        toData = new FileData(pathTo, hash);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display(DateFormat df)
    {
        // FE3
        if (fromData.parentFolder.equals(toData.parentFolder))
        {
            return fmt(RENAME_FMT, tsFmt(df), fromData.name, fromData.parentFolder, toData.name);
        }
        else
        {
            return fmt(MOVE_FMT, tsFmt(df), toData.name, fromData.parentFolder, toData.parentFolder);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof FileMoveEvent && super.equals(obj))
        {
            FileMoveEvent other = (FileMoveEvent) obj;
            return other.toData.hash.equals(toData.hash);
        }
        return false;
    }
}
