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
package it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;
import it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators.CompositeOverheadCalculator.ListComparable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author gcatania
 */
public class CompositeOverheadCalculator<T extends Comparable<T>> implements OverheadCalculator<ListComparable<T>>
{

    private final OverheadCalculator<T>[] calculators;

    public CompositeOverheadCalculator(OverheadCalculator<T>... calculators)
    {
        this.calculators = calculators;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListComparable<T> getOverhead(Dropbox dropbox, CartesianRectangle rect)
    {
        List<T> output = new ArrayList<T>(calculators.length);
        for (OverheadCalculator<T> c : calculators)
        {
            output.add(c.getOverhead(dropbox, rect));
        }
        return new ListComparable<T>(output);
    }

    public static class ListComparable<T extends Comparable<T>> implements Comparable<ListComparable<T>>
    {
        private final List<T> l;

        private ListComparable(List<T> l)
        {
            this.l = l;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(ListComparable<T> o)
        {
            Iterator<T> thisIter = l.iterator();
            Iterator<T> otherIter = o.l.iterator();
            while (thisIter.hasNext()) // only handles same length lists now
            {
                int itemCompare = thisIter.next().compareTo(otherIter.next());
                if (itemCompare != 0)
                {
                    return itemCompare;
                }
            }
            return 0;
        }

    }

}
