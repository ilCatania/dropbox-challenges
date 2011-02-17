package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;

import java.text.DateFormat;
import java.text.MessageFormat;


/**
 * @author gcatania
 */
public class DirectoryMoveEvent extends MoveEvent implements DirectoryEvent
{

    // warning: not thread safe
    private static final MessageFormat FMT = new MessageFormat(
        "{0}: directory {1} moved from {2} to {3} (with {4} contained files and {5} contained directories).");

    public final DirectoryData fromData;

    public final DirectoryData data;

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
        data = (DirectoryData) super.data;
        fromData = (DirectoryData) super.fromData;
        this.movedChildFiles = movedChildFiles;
        this.movedChildDirectories = movedChildDirectories;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display(DateFormat df)
    {
        return fmt(
            FMT,
            tsFmt(df),
            data.name,
            fromData.parentFolder,
            data.parentFolder,
            movedChildFiles,
            movedChildDirectories);
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
