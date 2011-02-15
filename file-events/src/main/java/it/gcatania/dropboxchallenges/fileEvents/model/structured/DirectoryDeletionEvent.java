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

    /**
     * the deleted data
     */
    public final DirectoryData data;

    private int deletedChildFiles;

    private int deletedChildDirectories;

    public DirectoryDeletionEvent(RawEvent ev)
    {
        super(ev);
        data = (DirectoryData) super.data;
        deletedChildFiles = 0;
        deletedChildDirectories = 0;
    }

    public DirectoryDeletionEvent(long timeStamp, String path)
    {
        super(timeStamp, path, FileSystemData.DIRECTORY_HASH);
        data = (DirectoryData) super.data;
        deletedChildFiles = 0;
        deletedChildDirectories = 0;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof DirectoryDeletionEvent && super.equals(obj))
        {
            DirectoryDeletionEvent other = (DirectoryDeletionEvent) obj;
            return other.deletedChildFiles == deletedChildFiles
                && other.deletedChildDirectories == deletedChildDirectories;
        }
        return false;
    }
}
