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

import it.gcatania.dropboxchallenges.packingYourDropbox.model.Rectangle;

import java.util.Comparator;


/**
 * compares two rectangle by first comparing their respective longer sidea, falling back to comparing their respective
 * shorter sides if the longer ones equal.
 * @author gcatania
 */
public class RectangleMaxSideComparator implements Comparator<Rectangle>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Rectangle o1, Rectangle o2)
    {
        long min1;
        long max1;
        if (o1.getWidth() > o1.getHeight())
        {
            max1 = o1.getWidth();
            min1 = o1.getHeight();
        }
        else
        {
            max1 = o1.getHeight();
            min1 = o1.getWidth();
        }
        long min2;
        long max2;
        if (o2.getWidth() > o2.getHeight())
        {
            max2 = o2.getWidth();
            min2 = o2.getHeight();
        }
        else
        {
            max2 = o2.getHeight();
            min2 = o2.getWidth();
        }

        int compareMax = Long.signum(max1 - max2);
        if (compareMax == 0)
        {
            return Long.signum(min1 - min2);
        }
        return compareMax;
    }

}
