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
public class DirectoryDeletionEvent extends StructuredEvent
{

    // warning: not thread safe
    private static final MessageFormat FMT = new MessageFormat(
        "{0}: directory {1} deleted from {2} (with {3} contained files and {4} contained directories).");

    private final int deletedChildFiles;

    private final int deletedChildDirectories;

    public final DirectoryData deletedData;

    public DirectoryDeletionEvent(long timeStamp, String path, int deletedChildFiles, int deletedChildDirectories)
    {
        super(timeStamp);
        deletedData = new DirectoryData(path);
        this.deletedChildFiles = deletedChildFiles;
        this.deletedChildDirectories = deletedChildDirectories;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display(DateFormat df)
    {
        return fmt(FMT, tsFmt(df), deletedData.name, deletedData.parentPath, deletedChildFiles, deletedChildDirectories);
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
            return other.deletedData.equals(deletedData)
                && other.deletedChildFiles == deletedChildFiles
                && other.deletedChildDirectories == deletedChildDirectories;
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
            .append("DirectoryDeletionEvent [deletedChildFiles=")
            .append(deletedChildFiles)
            .append(", deletedChildDirectories=")
            .append(deletedChildDirectories)
            .append(", deletedData=")
            .append(deletedData)
            .append(", timeStamp=")
            .append(timeStamp)
            .append("]")
            .toString();
    }

}
