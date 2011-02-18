package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;

import java.text.DateFormat;
import java.text.MessageFormat;


/**
 * @author gcatania
 */
public class DirectoryRenameEvent extends MoveEvent
{

    // warning: not thread safe
    private static final MessageFormat RENAME_FMT = new MessageFormat("{0}: directory {1} in {2} renamed to {3}.");

    public final DirectoryData fromData;

    public final DirectoryData toData;

    public DirectoryRenameEvent(long timeStamp, String pathFrom, String pathTo)
    {
        super(timeStamp, pathFrom, pathTo);
        fromData = new DirectoryData(pathFrom);
        toData = new DirectoryData(pathTo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display(DateFormat df)
    {
        return fmt(RENAME_FMT, tsFmt(df), fromData.name, fromData.parentPath, toData.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof DirectoryRenameEvent && super.equals(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return new StringBuilder()
            .append("DirectoryRenameEvent [fromData=")
            .append(fromData)
            .append(", toData=")
            .append(toData)
            .append(", timeStamp=")
            .append(timeStamp)
            .append("]")
            .toString();
    }

}
