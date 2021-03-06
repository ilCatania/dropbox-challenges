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
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

import java.text.DateFormat;
import java.text.MessageFormat;


/**
 * @author gcatania
 */
public class FileContentChangeEvent extends StructuredEvent
{

    // warning: not thread safe
    private static final MessageFormat FMT = new MessageFormat("{0}: file {1} in {2} modified.");

    public final FileData modifiedData;

    public FileContentChangeEvent(long timeStamp, String path, String hash)
    {
        super(timeStamp);
        modifiedData = new FileData(path, hash);
    }

    public FileContentChangeEvent(RawEvent addEv)
    {
        this(addEv.timeStamp, addEv.path, addEv.hash);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display(DateFormat df)
    {
        return fmt(FMT, tsFmt(df), modifiedData.name, modifiedData.parentPath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof FileContentChangeEvent && super.equals(obj))
        {
            FileContentChangeEvent other = (FileContentChangeEvent) obj;
            return other.modifiedData.equals(modifiedData);
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
            .append("FileContentChangeEvent [modifiedData=")
            .append(modifiedData)
            .append(", timeStamp=")
            .append(timeStamp)
            .append("]")
            .toString();
    }

}
