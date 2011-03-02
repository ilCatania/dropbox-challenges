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
import java.text.MessageFormat;


/**
 * @author gcatania
 */
public class DirectoryCreationEvent extends StructuredEvent
{

    // warning: not thread safe
    private static final MessageFormat FMT = new MessageFormat("{0}: directory {1} created in {2}.");

    public final DirectoryData createdData;

    public DirectoryCreationEvent(long timeStamp, String path)
    {
        super(timeStamp);
        createdData = new DirectoryData(path);
    }

    public DirectoryCreationEvent(RawEvent ev)
    {
        this(ev.timeStamp, ev.path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display(DateFormat df)
    {
        return fmt(FMT, tsFmt(df), createdData.name, createdData.parentPath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof DirectoryCreationEvent && super.equals(obj))
        {
            DirectoryCreationEvent other = (DirectoryCreationEvent) obj;
            return other.createdData.equals(createdData);
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
            .append("DirectoryCreationEvent [createdData=")
            .append(createdData)
            .append(", timeStamp=")
            .append(timeStamp)
            .append("]")
            .toString();
    }

}
