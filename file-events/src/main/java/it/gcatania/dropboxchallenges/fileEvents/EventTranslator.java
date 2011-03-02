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
package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.CascadingDirectoryEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.DirectoryCreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileContentChangeEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileCreationEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileDeletionEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileMoveEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.FileRenameEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.StructuredEvent;

import java.util.ArrayList;
import java.util.List;


/**
 * main engine class for event translation.
 * @author gcatania
 */
public class EventTranslator
{

    /**
     * translates a list of raw events into structured ones.
     * @param events the raw events
     * @return the structured ones
     */
    public List<StructuredEvent> translate(List<RawEvent> events)
    {
        List<StructuredEvent> output = new ArrayList<StructuredEvent>();
        StructuredEvent lastEv = null;

        for (RawEvent ev : events)
        {
            lastEv = ev.isDel ? handleDel(ev, lastEv, output) : handleAdd(ev, lastEv, output);
        }

        if (lastEv instanceof CascadingDirectoryEvent)
        {
            CascadingDirectoryEvent cEv = (CascadingDirectoryEvent) lastEv;
            output.addAll(cEv.getEvents());
        }
        else if (lastEv != null)
        {
            output.add(lastEv);
        }
        return output;
    }

    /**
     * handles a raw deletion event
     * @param delEv the deletion event
     * @param lastEv the last structured event created
     * @param output the structured event output
     * @return a created structured event to replace the last one
     */
    private StructuredEvent handleDel(RawEvent delEv, StructuredEvent lastEv, List<StructuredEvent> output)
    {
        if (lastEv instanceof CascadingDirectoryEvent)
        {
            // if there is a directory move/delete pending, check if this add fits in
            CascadingDirectoryEvent cEv = (CascadingDirectoryEvent) lastEv;
            if (cEv.addDeletion(delEv))
            {
                return lastEv; // last event may not be competed yet
            }
            else
            {
                output.addAll(cEv.getEvents());
                lastEv = null;
            }
        }

        // if we made it here, the last event is completed
        if (lastEv != null)
        {
            output.add(lastEv);
        }
        return delEv.isDirectory ? new CascadingDirectoryEvent(delEv) : new FileDeletionEvent(delEv);
    }

    /**
     * handles a raw creation event
     * @param delEv the deletion event
     * @param lastEv the last structured event created
     * @param output the structured event output
     * @return a created structured event to replace the last one
     */
    private StructuredEvent handleAdd(RawEvent addEv, StructuredEvent lastEv, List<StructuredEvent> output)
    {
        if (lastEv instanceof CascadingDirectoryEvent)
        {
            // if there is a directory move pending, check if this add fits in
            CascadingDirectoryEvent cEv = (CascadingDirectoryEvent) lastEv;
            if (cEv.addCreation(addEv))
            {
                return lastEv; // last event may not be competed yet
            }
            else
            {
                output.addAll(cEv.getEvents());
                lastEv = null;
            }
        }
        else if (lastEv instanceof FileDeletionEvent)
        {
            // if there is a file move/rename/edit pending, check if this add completes it
            FileDeletionEvent fileDelEv = (FileDeletionEvent) lastEv;
            if (addEv.hash.equals(fileDelEv.deletedData.hash)) // FE3
            {
                if (fileDelEv.deletedData.sameName(addEv.path))
                {
                    return new FileMoveEvent(addEv.timeStamp, fileDelEv.deletedData.fullPath, addEv.path, addEv.hash);
                }
                else if (fileDelEv.deletedData.sameParentPath(addEv.path))
                {
                    return new FileRenameEvent(addEv.timeStamp, fileDelEv.deletedData.fullPath, addEv.path, addEv.hash);
                }
            }
            else if (addEv.path.equals(fileDelEv.deletedData.fullPath)) // FE2
            {
                return new FileContentChangeEvent(addEv);
            }
        }

        // if we made it here, the last event is completed
        if (lastEv != null)
        {
            output.add(lastEv);
        }
        return addEv.isDirectory ? new DirectoryCreationEvent(addEv) : new FileCreationEvent(addEv);
    }

}
