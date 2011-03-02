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
import it.gcatania.dropboxchallenges.packingYourDropbox.model.PreAllocatingDropbox;


/**
 * a "magic" overhead calculator that calculates overhead by area first, and then with some complex criteria explained
 * in
 * comments
 * @author gcatania
 */
public class DropboxMagicOverheadCalculator extends CompositeOverheadCalculator<Long>
{

    @SuppressWarnings("unchecked")
    public DropboxMagicOverheadCalculator()
    {
        super(new DropboxAreaOverheadCalculator(), new OverheadCalculator<Long>()
        {

            @Override
            public Long getOverhead(Dropbox dropbox, CartesianRectangle rect)
            {
                if (dropbox instanceof PreAllocatingDropbox)
                {
                    // minimize the distance from the pre-allocated perimeter
                    PreAllocatingDropbox pdb = (PreAllocatingDropbox) dropbox;
                    long deltaX = Math.abs(pdb.preAllocatedWidth - rect.getUpperRight().getX());
                    long deltaY = Math.abs(pdb.preAllocatedHeight - rect.getUpperRight().getY());
                    return Math.min(deltaX, deltaY);
                }
                // minimize the distance between the rectangle and dropbox's respective upper right corners
                return -Math.max(rect.getUpperRight().getX(), rect.getUpperRight().getY());
            }
        });
    }
}
