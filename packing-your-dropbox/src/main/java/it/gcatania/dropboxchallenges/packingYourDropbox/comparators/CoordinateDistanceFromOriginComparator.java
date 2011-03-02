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
package it.gcatania.dropboxchallenges.packingYourDropbox.comparators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.Coordinates;

import java.util.Comparator;


/**
 * @author gcatania
 */
public class CoordinateDistanceFromOriginComparator implements Comparator<Coordinates>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Coordinates o1, Coordinates o2)
    {
        long x1 = o1.getX();
        long y1 = o1.getY();
        long squareDistance1 = x1 * x1 + y1 * y1;
        long x2 = o2.getX();
        long y2 = o2.getY();
        long squareDistance2 = x2 * x2 + y2 * y2;
        return Long.signum(squareDistance1 - squareDistance2);
    }

}
