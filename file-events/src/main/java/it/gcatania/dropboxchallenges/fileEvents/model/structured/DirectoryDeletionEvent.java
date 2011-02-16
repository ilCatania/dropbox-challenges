package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.Queue;


/**
 * @author gcatania
 */
public class DirectoryDeletionEvent extends DeletionEvent implements DirectoryEvent
{

    // warning: not thread safe
    private static final MessageFormat FMT = new MessageFormat(
        "{0}: directory {1} deleted from {2} (with {3} contained files and {4} contained directories).");

    public final DirectoryData data;

    public final Queue<String> deletedChildFiles;

    public final Queue<String> deletedChildDirectories;

    public DirectoryDeletionEvent(RawEvent ev)
    {
        this(ev.timeStamp, ev.path);
    }

    public DirectoryDeletionEvent(long timeStamp, String path)
    {
        super(timeStamp, path);
        data = (DirectoryData) super.data;
        deletedChildFiles = new LinkedList<String>();
        deletedChildDirectories = new LinkedList<String>();
    }

    public void addDeletion(String deletedPath, boolean directory)
    {
        Queue<String> q = directory ? deletedChildDirectories : deletedChildFiles;
        q.add(deletedPath);
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
            data.parentFolder,
            deletedChildFiles.size(),
            deletedChildDirectories.size());
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
            return other.deletedChildFiles.equals(deletedChildFiles)
                && other.deletedChildDirectories.equals(deletedChildDirectories);
        }
        return false;
    }
}
