package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.FileSystemData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

/**
 * @author gcatania
 */
public class DirectoryMoveEvent extends MoveEvent
{

    private int movedChildFiles;

    private int movedChildDirectories;

    public DirectoryMoveEvent(RawEvent delEvent, RawEvent addEvent)
    {
        super(delEvent, addEvent);
        movedChildFiles = 0;
        movedChildDirectories = 0;
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
}
