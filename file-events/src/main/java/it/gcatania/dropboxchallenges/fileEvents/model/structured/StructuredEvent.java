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

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;


/**
 * Base class in the hierarchy of structured events.
 * @author gcatania
 */
public abstract class StructuredEvent
{

    public final long timeStamp;

    public StructuredEvent(long timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return new Long(timeStamp).hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof StructuredEvent)
        {
            StructuredEvent other = (StructuredEvent) obj;
            return other.timeStamp == timeStamp;
        }
        return false;
    }

    public abstract String display(DateFormat df);

    protected final String fmt(MessageFormat format, Object... args)
    {
        return format.format(args);
    }

    protected final String tsFmt(DateFormat df)
    {
        return df.format(new Date(timeStamp));
    }

}
