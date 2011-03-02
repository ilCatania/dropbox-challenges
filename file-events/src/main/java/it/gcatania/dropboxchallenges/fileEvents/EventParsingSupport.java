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

import it.gcatania.dropboxchallenges.common.ParsingSupport;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEventType;


/**
 * helper class for parsing raw events.
 * @author gcatania
 */
public class EventParsingSupport extends ParsingSupport<RawEvent>
{

    public EventParsingSupport()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RawEvent getObject(String objectLine) throws IllegalArgumentException
    {
        String[] eventData = objectLine.split(" ");
        if (eventData.length != 4)
        {
            throw new IllegalArgumentException("Cannot read event data from: " + objectLine);
        }

        RawEventType eventType = RawEventType.valueOf(eventData[0]);
        long timeStamp = Long.parseLong(eventData[1]);
        String path = eventData[2];
        String hash = eventData[3];
        return new RawEvent(eventType, timeStamp, path, hash);
    }
}
