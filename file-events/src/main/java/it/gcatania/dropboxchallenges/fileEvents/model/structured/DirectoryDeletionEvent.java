package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;
import it.gcatania.dropboxchallenges.fileEvents.model.FileData;
import it.gcatania.dropboxchallenges.fileEvents.model.FileSystemData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;


/**
 * @author gcatania
 */
public class DirectoryDeletionEvent extends DeletionEvent implements DirectoryEvent
{

    private final DirectoryData data;

    private int deletedChildFiles;

    private int deletedChildDirectories;

    public DirectoryDeletionEvent(RawEvent ev)
    {
        data = (DirectoryData) ev.data;
        deletedChildFiles = 0;
        deletedChildDirectories = 0;
    }

    @Override
    public DirectoryData getData()
    {
        return data;
    }

    public void addDeletion(FileSystemData deletedData)
    {
        if (deletedData instanceof FileData)
        {
            deletedChildFiles++;
        }
        else
        {
            deletedChildDirectories++;
        }
    }

}
