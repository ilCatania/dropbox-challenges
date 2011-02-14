package it.gcatania.dropboxchallenges.fileEvents.model;

/**
 * @author gcatania
 */
public class DirectoryDeletionEvent extends DeletionEvent
{

    public final DirectoryData deletedData;

    private int deletedChildFiles;

    private int deletedChildDirectories;

    public DirectoryDeletionEvent(RawEvent ev)
    {
        super(ev);
        deletedData = (DirectoryData) ev.data;
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

}
