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

import it.gcatania.dropboxchallenges.fileEvents.model.structured.StructuredEvent;

import java.text.DateFormat;
import java.util.List;


/**
 * standard display helper for structured events.
 * @author gcatania
 */
public class EventDisplayer
{

    /**
     * prints the descriptions for the input structured events to standard output.
     * @param events the events to display
     */
    public void display(List< ? extends StructuredEvent> events)
    {
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        for (StructuredEvent se : events)
        {
            System.out.println(se.display(df));
        }
    }

}
