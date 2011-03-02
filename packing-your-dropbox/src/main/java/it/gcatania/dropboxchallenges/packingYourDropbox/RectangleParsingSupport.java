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
package it.gcatania.dropboxchallenges.packingYourDropbox;

import it.gcatania.dropboxchallenges.common.ParsingSupport;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Rectangle;


/**
 * rectangle parsing support
 * @author gcatania
 */
public class RectangleParsingSupport extends ParsingSupport<Rectangle>
{

    public RectangleParsingSupport()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Rectangle getObject(String objectLine) throws IllegalArgumentException
    {
        String[] dimStrings = objectLine.split(" ");
        if (dimStrings.length != 2)
        {
            throw new IllegalArgumentException("cannot extract dimensions from: " + objectLine);
        }
        int width = Integer.parseInt(dimStrings[0]);
        int height = Integer.parseInt(dimStrings[1]);
        return new Rectangle(width, height);
    }

}
