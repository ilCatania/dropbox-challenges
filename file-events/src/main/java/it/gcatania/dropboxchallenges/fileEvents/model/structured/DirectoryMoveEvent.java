package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;

import java.text.DateFormat;
import java.text.MessageFormat;


/**
 * @author gcatania
 */
public class DirectoryMoveEvent extends MoveEvent
{

    // warning: not thread safe
    private static final MessageFormat RENAME_FMT = new MessageFormat("{0}: directory {1} in {2} renamed to {3}.");

    // warning: not thread safe
    private static final MessageFormat MOVE_FMT = new MessageFormat(
        "{0}: directory {1} moved to {2} (with {3} contained files and {4} contained directories).");

    public final DirectoryData fromData;

    public final DirectoryData toData;

    private final int movedChildFiles;

    private final int movedChildDirectories;

    public DirectoryMoveEvent(
        long timeStamp,
        String pathFrom,
        String pathTo,
        int movedChildFiles,
        int movedChildDirectories)
    {
        super(timeStamp, pathFrom, pathTo);
        fromData = new DirectoryData(pathFrom);
        toData = new DirectoryData(pathTo);
        this.movedChildFiles = movedChildFiles;
        this.movedChildDirectories = movedChildDirectories;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display(DateFormat df)
    {
        if (fromData.parentFolder.equals(toData.parentFolder))
        {

            return fmt(RENAME_FMT, tsFmt(df), fromData.name, fromData.parentFolder, toData.name);
        }
        else
        {
            return fmt(MOVE_FMT, tsFmt(df), fromData.fullPath, toData.fullPath, movedChildFiles, movedChildDirectories);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof DirectoryMoveEvent && super.equals(obj))
        {
            DirectoryMoveEvent other = (DirectoryMoveEvent) obj;
            return other.movedChildFiles == movedChildFiles && other.movedChildDirectories == movedChildDirectories;
        }
        return false;
    }

}
