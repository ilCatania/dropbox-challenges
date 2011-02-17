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
 * an internal structured event type used as a temporary container for event sequences that do not identify a structured
 * event type yet.
 * @author gcatania
 */
public class CascadingDirectoryEvent extends StructuredEvent implements DirectoryEvent
{

    /**
     * the data this event is initialized with upon first deletion
     */
    public final DirectoryData data;

    private final Queue<RawEvent> childDeleteEvents;

    private final Queue<RawEvent> childCreateEvents;

    /**
     * the number of directories marked for deletion received by this event
     */
    private int numMaxDirs;

    /**
     * the number of files marked for deletion received by this event
     */
    private int numMaxFiles;

    /**
     * the current add state: once an add event is added, no more delete events can be added
     */
    private boolean adding;

    /**
     * compiled pattern used to detect path moves is cached here for performance
     */
    private Pattern fromPattern;

    /**
     * populated on first add, the candidate path we are moving from
     */
    private String pathFrom;

    /**
     * populated on first add, the candidate path we are moving to
     */
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
        List<StructuredEvent> output = new ArrayList<StructuredEvent>(2 + childCreateEvents.size());
        output.add(new DirectoryDeletionEvent(timeStamp, data.fullPath, numMaxFiles, numMaxDirs));
        if (adding)
        {
            output.add(new DirectoryCreationEvent(timeStamp, pathTo));
        }
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
