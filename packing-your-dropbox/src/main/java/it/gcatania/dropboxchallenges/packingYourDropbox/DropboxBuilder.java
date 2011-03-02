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

import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.ReverseComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Coordinates;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.PreAllocatingDropbox;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Rectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators.OverheadCalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * main engine class for dropbox building.
 * @author gcatania
 */
public class DropboxBuilder
{

    private final Comparator<Rectangle> rectangleComparator;

    private final OverheadCalculator< ? > overheadCalculator;

    /**
     * @param rectangleComparator rectangles will be added to the dropbox according to the order imposed by the reverse
     * of this comparator. For example, if the comparator compares areas, rectangles will be added starting from the one
     * with greater area.
     * @param overheadCalculator every time a rectangle is added, the builder will choose between the available
     * positions the one that minimizes the overhead calculated by this calculator. Not used in brute force approach.
     */
    public DropboxBuilder(Comparator<Rectangle> rectangleComparator, OverheadCalculator< ? > overheadCalculator)
    {
        this.rectangleComparator = rectangleComparator;
        this.overheadCalculator = overheadCalculator;
    }

    /**
     * standard dropbox build method
     * @param rectangles the rectangles to build a dropbox from
     * @return the best dropbox containing the rectangles
     */
    public Dropbox build(List<Rectangle> rectangles)
    {
        return internalOptimizedBuild(rectangles, new Dropbox());
    }

    /**
     * standard dropbox build method that pre-allocates a rectangular dropbox with an area equal to the total rectangle
     * area
     * @param rectangles the rectangles to build a dropbox from
     * @return the best dropbox containing the rectangles
     */
    public Dropbox buildWithPreAllocation(List<Rectangle> rectangles)
    {
        long totalRectangleArea = 0;
        for (Rectangle rect : rectangles)
        {
            totalRectangleArea += rect.getArea();
        }
        long a = (long) Math.sqrt(totalRectangleArea);

        while (totalRectangleArea % a != 0)
        {
            a--;
        }
        long b = totalRectangleArea / a;

        return internalOptimizedBuild(rectangles, new PreAllocatingDropbox(a, b));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Dropbox internalOptimizedBuild(List<Rectangle> rectangles, Dropbox dropbox)
    {
        Collections.sort(rectangles, new ReverseComparator<Rectangle>(rectangleComparator));
        for (Rectangle rect : rectangles)
        {
            Set<Coordinates> availableStartingPoints = dropbox.getAvailableStartingPoints();
            if (availableStartingPoints.size() == 1)
            {
                dropbox.put(new CartesianRectangle(availableStartingPoints.iterator().next(), rect));
                continue;
            }

            Comparable<Object> minOverhead = null;
            CartesianRectangle minOverheadRect = null;
            for (Coordinates coords : availableStartingPoints)
            {
                CartesianRectangle cRect = new CartesianRectangle(coords, rect);
                Comparable currOverhead = overheadCalculator.getOverhead(dropbox, cRect);
                if ((minOverhead == null || currOverhead.compareTo(minOverhead) < 0) && !dropbox.overlaps(cRect))
                {
                    minOverhead = currOverhead;
                    minOverheadRect = cRect;
                }
                // if not square, also try with rotation
                if (!rect.isSquare())
                {
                    cRect = new CartesianRectangle(coords, rect.rotate());
                    currOverhead = overheadCalculator.getOverhead(dropbox, cRect);
                    if ((minOverhead == null || currOverhead.compareTo(minOverhead) < 0) && !dropbox.overlaps(cRect))
                    {
                        minOverhead = currOverhead;
                        minOverheadRect = cRect;
                    }
                }
            }
            dropbox.put(minOverheadRect);
        }
        return dropbox;
    }

    /**
     * "brute force" dropbox building method. After sorting the rectangles according to the comparator, attempts to
     * place every rectangle in every possible position. This method takes multiple orders of magnitude longer than the
     * others depending on the number of rectangles passed.
     * @param rectangles the rectangles to build a dropbox from
     * @return the best dropbox containing the rectangles
     */
    public Dropbox bruteForceBuild(List<Rectangle> rectangles)
    {
        Collections.sort(rectangles, new ReverseComparator<Rectangle>(rectangleComparator));
        List<Dropbox> boxes = Collections.singletonList(new Dropbox());
        for (Rectangle rect : rectangles)
        {
            List<Dropbox> newBoxes = new ArrayList<Dropbox>();
            for (Dropbox db : boxes)
            {
                Set<Coordinates> availableStartingPoints = db.getAvailableStartingPoints();
                for (Coordinates coords : availableStartingPoints)
                {
                    addToBoxes(rect, coords, newBoxes, db);
                    if (!rect.isSquare())
                    {
                        addToBoxes(rect.rotate(), coords, newBoxes, db);
                    }
                }
            }
            boxes = newBoxes;
        }
        return findBest(boxes);
    }

    private static void addToBoxes(Rectangle rect, Coordinates coords, List<Dropbox> newBoxes, Dropbox db)
    {
        CartesianRectangle cRect = new CartesianRectangle(coords, rect);
        if (!db.overlaps(cRect))
        {
            Dropbox clone = db.clone();
            clone.put(cRect);
            newBoxes.add(clone);
        }
    }

    private Dropbox findBest(List<Dropbox> boxes)
    {
        Iterator<Dropbox> boxesIter = boxes.iterator();
        Dropbox min = boxesIter.next();
        long minArea = min.getArea();
        for (Dropbox db : boxes)
        {
            long currArea = db.getArea();
            if (currArea < minArea)
            {
                min = db;
                minArea = currArea;
            }
        }
        return min;
    }
}
