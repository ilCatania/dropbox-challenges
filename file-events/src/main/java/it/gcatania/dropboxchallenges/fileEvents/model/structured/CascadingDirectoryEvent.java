/**
 * Copyright 2011 Gabriele Catania <gabriele.ctn@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
public class CascadingDirectoryEvent extends StructuredEvent
{

    /**
     * the data this event is initialized with upon first deletion
     */
    public final DirectoryData firstDelData;

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
        super(ev.timeStamp);
        firstDelData = new DirectoryData(ev.path);
        childDeleteEvents = new LinkedList<RawEvent>();
        childCreateEvents = new LinkedList<RawEvent>();
        adding = false;
    }

    /**
     * attempts to add a raw deletion event to this event.
     * @param delEv the raw deletion event to add
     * @return true if the event was successfully added, false otherwise
     */
    public boolean addDeletion(RawEvent delEv)
    {
        if (adding || !firstDelData.contains(delEv.path))
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

    /**
     * attempts to add a raw creation event to this event.
     * @param delEv the raw creation event to add
     * @return true if the event was successfully added, false otherwise
     */
    public boolean addCreation(RawEvent addEv)
    {
        if (!adding)
        {
            adding = true;
            pathFrom = firstDelData.fullPath;
            pathTo = addEv.path;
            if (firstDelData.contains(pathTo))// FE11
            {
                throw new IllegalArgumentException("Cannot move directory " + pathFrom + " into subdirectory " + pathTo);
            }
            fromPattern = Pattern.compile(pathFrom, Pattern.LITERAL);
            return true;
        }

        RawEvent delEv = childDeleteEvents.poll(); // FE7
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
        // if all the child paths being deleted have also been readded, it's a directory move/rename
        if (adding && childCreateEvents.size() == (numMaxFiles + numMaxDirs)) // FE12
        {
            StructuredEvent ev;
            if (firstDelData.sameParentPath(pathTo))
            {
                ev = new DirectoryRenameEvent(timeStamp, pathFrom, pathTo);
            }
            else
            {
                ev = new DirectoryMoveEvent(timeStamp, pathFrom, pathTo, numMaxFiles, numMaxDirs);
            }
            return Collections.singletonList(ev);
        }

        // otherwise it's a directory deletion followed by (potentially) multiple atomic creations
        List<StructuredEvent> output = new ArrayList<StructuredEvent>(2 + childCreateEvents.size());
        output.add(new DirectoryDeletionEvent(timeStamp, firstDelData.fullPath, numMaxFiles, numMaxDirs));
        if (adding)
        {
            output.add(new DirectoryCreationEvent(timeStamp, pathTo));
            for (RawEvent ev : childCreateEvents)
            {
                output.add(ev.isDirectory ? new DirectoryCreationEvent(ev) : new FileCreationEvent(ev));
            }
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
