package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Queue;
import java.util.regex.Pattern;


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

    private final Queue<String> childFilesLeftToMove;

    private final Queue<String> childDirectoriesLeftToMove;

    private final int movedChildDirectories;

    private final int movedChildFiles;

    private Pattern toPattern;

    public DirectoryMoveEvent(
        long timeStamp,
        String pathFrom,
        String pathTo,
        Queue<String> deletedChildFiles,
        Queue<String> deletedChildDirectories)
    {
        super(timeStamp, pathFrom, pathTo);
        data = (DirectoryData) super.data;
        fromData = (DirectoryData) super.fromData;
        childFilesLeftToMove = deletedChildFiles;
        childDirectoriesLeftToMove = deletedChildDirectories;
        movedChildFiles = deletedChildFiles.size();
        movedChildDirectories = deletedChildDirectories.size();
        toPattern = Pattern.compile(fullPathTo, Pattern.LITERAL);
    }

    public DirectoryMoveEvent(DirectoryDeletionEvent delEv, RawEvent firstAddEv)
    {
        this(
            firstAddEv.timeStamp,
            delEv.data.fullPath,
            firstAddEv.path,
            delEv.deletedChildFiles,
            delEv.deletedChildDirectories);
    }

    public boolean addMove(String path, boolean directory)
    {
        Queue<String> q = directory ? childDirectoriesLeftToMove : childFilesLeftToMove;
        String pathBeforeMove = toPattern.matcher(path).replaceFirst(fullPathFrom);
        return pathBeforeMove.equals(q.poll());
    }

    public boolean isChildMove(RawEvent delEvent, RawEvent addEvent)
    {
        return delEvent.hash.equals(addEvent.hash)
            && toPattern.matcher(delEvent.path).replaceFirst(fullPathTo).equals(addEvent.path);
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
            return other.movedChildFiles == movedChildFiles
                && other.movedChildDirectories == movedChildDirectories
                && other.childFilesLeftToMove.equals(childFilesLeftToMove)
                && other.childDirectoriesLeftToMove.equals(childDirectoriesLeftToMove);
        }
        return false;
    }

}
