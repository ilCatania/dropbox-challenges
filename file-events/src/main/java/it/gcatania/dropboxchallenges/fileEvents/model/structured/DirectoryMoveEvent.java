package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

import java.util.regex.Pattern;


/**
 * @author gcatania
 */
public class DirectoryMoveEvent extends MoveEvent implements DirectoryEvent
{

    public final DirectoryData fromData;

    public final DirectoryData data;

    private int movedChildFiles;

    private int movedChildDirectories;

    private Pattern fromPattern;

    public DirectoryMoveEvent(RawEvent delEvent, RawEvent addEvent)
    {
        super(delEvent, addEvent);
        data = (DirectoryData) super.data;
        fromData = (DirectoryData) super.fromData;
        movedChildFiles = 0;
        movedChildDirectories = 0;
    }

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

    public DirectoryMoveEvent(long timeStamp, String pathFrom, String pathTo)
    {
        this(timeStamp, pathFrom, pathTo, 0, 0);
    }

    public void addMove(boolean directory)
    {
        if (directory)
        {
            movedChildDirectories++;
        }
        else
        {
            movedChildFiles++;
        }
    }

    public boolean isChildMove(RawEvent delEvent, RawEvent addEvent)
    {
        if (fromPattern == null)
        {
            fromPattern = Pattern.compile(fullPathFrom, Pattern.LITERAL);
        }
        return delEvent.hash.equals(addEvent.hash)
            && fromPattern.matcher(delEvent.path).replaceFirst(fullPathTo).equals(addEvent.path);
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
