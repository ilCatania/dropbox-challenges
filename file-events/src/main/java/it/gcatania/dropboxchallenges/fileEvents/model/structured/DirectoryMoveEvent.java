package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;
import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.FileSystemData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public class DirectoryMoveEvent extends MoveEvent implements DirectoryEvent
{

    public final DirectoryData data;

    private int movedChildFiles;

    private int movedChildDirectories;

    public DirectoryMoveEvent(RawEvent delEvent, RawEvent addEvent)
    {
        super(delEvent, addEvent);
        movedChildFiles = 0;
        movedChildDirectories = 0;
        data = (DirectoryData) super.data;
    }

    public DirectoryMoveEvent(
        long timeStamp,
        String pathFrom,
        String pathTo,
        int movedChildFiles,
        int movedChildDirectories)
    {
        super(timeStamp, pathFrom, pathTo, FileSystemData.DIRECTORY_HASH);
        data = (DirectoryData) super.data;
        this.movedChildFiles = movedChildFiles;
        this.movedChildDirectories = movedChildDirectories;
    }

    public DirectoryMoveEvent(long timeStamp, String pathFrom, String pathTo)
    {
        this(timeStamp, pathFrom, pathTo, 0, 0);
    }

    public void addMove(FileSystemData deletedData)
    {
        if (deletedData instanceof FileData)
        {
            movedChildFiles++;
        }
        else
        {
            movedChildDirectories++;
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
