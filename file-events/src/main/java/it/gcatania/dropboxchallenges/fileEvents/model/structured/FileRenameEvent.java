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

import it.gcatania.dropboxchallenges.fileEvents.model.FileData;

import java.text.DateFormat;
import java.text.MessageFormat;


/**
 * @author gcatania
 */
public class FileRenameEvent extends MoveEvent
{

    // warning: not thread safe
    private static final MessageFormat RENAME_FMT = new MessageFormat("{0}: file {0} in {1} renamed to {2}.");

    public final FileData fromData;

    public final FileData toData;

    public FileRenameEvent(long timeStamp, String pathFrom, String pathTo, String hash)
    {
        super(timeStamp, pathFrom, pathTo);
        fromData = new FileData(pathFrom, hash);
        toData = new FileData(pathTo, hash);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display(DateFormat df)
    {
        return fmt(RENAME_FMT, tsFmt(df), fromData.name, fromData.parentPath, toData.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof FileRenameEvent && super.equals(obj))
        {
            FileRenameEvent other = (FileRenameEvent) obj;
            return other.toData.hash.equals(toData.hash);
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
            .append("FileRenameEvent [fromData=")
            .append(fromData)
            .append(", toData=")
            .append(toData)
            .append(", timeStamp=")
            .append(timeStamp)
            .append("]")
            .toString();
    }
}
