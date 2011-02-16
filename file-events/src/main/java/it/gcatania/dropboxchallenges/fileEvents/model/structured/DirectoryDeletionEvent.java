package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

import java.text.DateFormat;
import java.text.MessageFormat;


/**
 * @author gcatania
 */
public class DirectoryDeletionEvent extends DeletionEvent implements DirectoryEvent
{

    // warning: not thread safe
    private static final MessageFormat FMT = new MessageFormat(
        "{0}: directory {1} deleted from {2} (with {3} contained files and {4} contained directories).");

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

    public DirectoryDeletionEvent(long timeStamp, String path, int deletedChildFiles, int deletedChildDirectories)
    {
        super(timeStamp, path);
        data = (DirectoryData) super.data;
        this.deletedChildFiles = deletedChildFiles;
        this.deletedChildDirectories = deletedChildDirectories;
    }

    public DirectoryDeletionEvent(long timeStamp, String path)
    {
        this(timeStamp, path, 0, 0);
    }

    public void addDeletion(boolean directory)
    {
        if (directory)
        {
            deletedChildDirectories++;
        }
        else
        {
            deletedChildFiles++;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display(DateFormat df)
    {
        return fmt(FMT, tsFmt(df), data.name, data.parentFolder, deletedChildFiles, deletedChildDirectories);
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
