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
package it.gcatania.dropboxchallenges.theDropboxDiet;

import it.gcatania.dropboxchallenges.common.ParsingSupport;


/**
 * 
 */

/**
 * @author gcatania
 */
public class DietItemParsingSupport extends ParsingSupport<DietItem>
{

    /**
     * {@inheritDoc}
     */
    @Override
    protected DietItem getObject(String objectLine) throws IllegalArgumentException
    {
        String[] itemStrings = objectLine.split(" ");
        if (itemStrings.length != 2)
        {
            throw new IllegalArgumentException("cannot extract diet item from: " + objectLine);
        }
        String itemName = itemStrings[0];
        int cals = Integer.parseInt(itemStrings[1]);
        return new DietItem(itemName, cals);
    }

}
