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

import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;

import java.util.Comparator;


/**
 * compares drop boxes by free space, with those having less free space scoring lower
 * @author gcatania
 */
public class DropboxFreeSpaceComparator implements Comparator<Dropbox>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Dropbox o1, Dropbox o2)
    {
        return Long.signum(o1.getFreeSpace() - o2.getFreeSpace());
    }

}
