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

import java.text.DateFormat;
import java.text.MessageFormat;


/**
 * @author gcatania
 */
public class DirectoryMoveEvent extends MoveEvent
{

    // warning: not thread safe
    private static final MessageFormat MOVE_FMT = new MessageFormat(
        "{0}: directory {1} moved from {2} to {3} (with {4} contained files and {5} contained directories).");

    public final DirectoryData fromData;

    public final DirectoryData toData;

    private final int movedChildFiles;

    private final int movedChildDirectories;

    public DirectoryMoveEvent(
        long timeStamp,
        String pathFrom,
        String pathTo,
        int movedChildFiles,
        int movedChildDirectories)
    {
        super(timeStamp, pathFrom, pathTo);
        fromData = new DirectoryData(pathFrom);
        toData = new DirectoryData(pathTo);
        this.movedChildFiles = movedChildFiles;
        this.movedChildDirectories = movedChildDirectories;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display(DateFormat df)
    {
        return fmt(
            MOVE_FMT,
            tsFmt(df),
            fromData.name,
            fromData.parentPath,
            toData.parentPath,
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
            return other.movedChildFiles == movedChildFiles && other.movedChildDirectories == movedChildDirectories;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return new StringBuilder()
            .append("DirectoryMoveEvent [fromData=")
            .append(fromData)
            .append(", toData=")
            .append(toData)
            .append(", movedChildFiles=")
            .append(movedChildFiles)
            .append(", movedChildDirectories=")
            .append(movedChildDirectories)
            .append(", timeStamp=")
            .append(timeStamp)
            .append("]")
            .toString();
    }

}
