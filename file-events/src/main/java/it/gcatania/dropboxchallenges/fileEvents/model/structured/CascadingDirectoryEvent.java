package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import it.gcatania.dropboxchallenges.fileEvents.model.DirectoryData;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;


/**
 * @author gcatania
 */
public class CascadingDirectoryEvent extends StructuredEvent implements DirectoryEvent
{

    public final DirectoryData data;

    private final Queue<RawEvent> childDeleteEvents;

    private final Queue<RawEvent> childCreateEvents;

    private int numMaxDirs;

    private int numMaxFiles;

    private boolean adding;

    private Pattern fromPattern;

    private String pathFrom;

    private String pathTo;

    public CascadingDirectoryEvent(RawEvent ev)
    {
        super(ev);
        data = (DirectoryData) super.data;
        childDeleteEvents = new LinkedList<RawEvent>();
        childCreateEvents = new LinkedList<RawEvent>();
        adding = false;
    }

    public boolean addDeletion(RawEvent delEv)
    {
        if (adding || !data.contains(delEv.path))
        {
            return false;
        }
        childDeleteEvents.add(delEv);
        if (delEv.isDirectory)
        {
            numMaxDirs++;
        }
        else
        {
            numMaxFiles++;
        }
        return true;
    }

    public boolean addCreation(RawEvent addEv)
    {
        if (!adding)
        {
            adding = true;
            pathFrom = data.fullPath;
            pathTo = addEv.path;
            // TODO throw an exception if pathTo contains pathFrom
            fromPattern = Pattern.compile(pathFrom, Pattern.LITERAL);
            return true;
        }

        RawEvent delEv = childDeleteEvents.poll();
        boolean previouslyDeleted = delEv != null && movePathsMatch(delEv, addEv) && delEv.hash.equals(addEv.hash);
        if (previouslyDeleted)
        {
            childCreateEvents.add(addEv);
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean movePathsMatch(RawEvent delEv, RawEvent addEv)
    {
        return fromPattern.matcher(delEv.path).replaceFirst(pathTo).equals(addEv.path);
    }

    public List<StructuredEvent> getEvents()
    {
        if (adding && childCreateEvents.size() == (numMaxFiles + numMaxDirs))
        {
            return Collections.<StructuredEvent> singletonList(new DirectoryMoveEvent(
                timeStamp,
                pathFrom,
                pathTo,
                numMaxFiles,
                numMaxDirs));
        }
        DirectoryDeletionEvent deletionEvent = new DirectoryDeletionEvent(
            timeStamp,
            data.fullPath,
            numMaxFiles,
            numMaxDirs);
        if (childCreateEvents.isEmpty())
        {
            return Collections.<StructuredEvent> singletonList(deletionEvent);
        }
        List<StructuredEvent> output = new ArrayList<StructuredEvent>(1 + childCreateEvents.size());
        output.add(deletionEvent);
        for (RawEvent ev : childCreateEvents)
        {
            output.add(ev.isDirectory ? new DirectoryCreationEvent(ev) : new FileCreationEvent(ev));
        }
        return output;
    }

    /**
     * not implemented since this is an internal class
     */
    @Override
    public String display(DateFormat df)
    {
        return null;
    }

}
