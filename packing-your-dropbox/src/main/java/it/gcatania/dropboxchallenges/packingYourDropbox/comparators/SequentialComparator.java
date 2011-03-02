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

import java.util.Comparator;


/**
 * a composite comparator that returns the first non-zero value of child comparators
 * @author gcatania
 */
public class SequentialComparator<T> implements Comparator<T>
{

    private final Comparator<T>[] comparators;

    public SequentialComparator(Comparator<T>... comparators)
    {
        this.comparators = comparators;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(T o1, T o2)
    {
        for (Comparator<T> comparator : comparators)
        {
            int result = comparator.compare(o1, o2);
            if (result != 0)
            {
                return result;
            }
        }
        return 0;
    }

}
