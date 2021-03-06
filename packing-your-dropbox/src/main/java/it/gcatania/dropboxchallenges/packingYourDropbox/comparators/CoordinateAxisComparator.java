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
/**
 * 
 */
package it.gcatania.dropboxchallenges.packingYourDropbox.comparators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.Coordinates;

import java.util.Comparator;


/**
 * compares coordinates by their x value first, and by their y value if the x's are equal.
 * @author gcatania
 */
public class CoordinateAxisComparator implements Comparator<Coordinates>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Coordinates arg0, Coordinates arg1)
    {
        int signX = Long.signum(arg0.getX() - arg1.getX());
        if (signX == 0)
        {
            return Long.signum(arg0.getY() - arg1.getY());
        }
        return signX;
    }

}
