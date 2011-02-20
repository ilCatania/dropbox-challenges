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
 * @author gcatania
 */
public class DropboxBuilder
{

    private final Comparator<Rectangle> rectangleComparator;

    private final OverheadCalculator overheadCalculator;

    public DropboxBuilder(Comparator<Rectangle> rectangleComparator, OverheadCalculator overheadCalculator)
    {
        this.rectangleComparator = rectangleComparator;
        this.overheadCalculator = overheadCalculator;
    }

    public Dropbox build(List<Rectangle> rectangles)
    {
        return internalBuild(rectangles, new Dropbox());
    }

    public Dropbox buildWithPreAllocation(List<Rectangle> rectangles)
    {
        PreAllocatingDropbox dropbox = new PreAllocatingDropbox();

        long totalRectangleArea = 0;
        for (Rectangle rect : rectangles)
        {
            totalRectangleArea += rect.getArea();
        }
        long preAllocation = (long) Math.sqrt(totalRectangleArea);
        dropbox.preAllocate(preAllocation, preAllocation);

        return internalBuild(rectangles, dropbox);
    }

    private Dropbox internalBuild(List<Rectangle> rectangles, Dropbox dropbox)
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

            long minOverhead = Long.MAX_VALUE;
            CartesianRectangle minOverheadRect = null;
            for (Coordinates coords : availableStartingPoints)
            {
                CartesianRectangle cRect = new CartesianRectangle(coords, rect);
                long currOverhead = overheadCalculator.getOverhead(dropbox, cRect);
                if (currOverhead < minOverhead && !dropbox.overlaps(cRect))
                {
                    minOverhead = currOverhead;
                    minOverheadRect = cRect;
                }
                // if not square, also try with rotation
                if (rect.isSquare())
                {
                    continue;
                }
                cRect = new CartesianRectangle(coords, rect.rotate());
                currOverhead = overheadCalculator.getOverhead(dropbox, cRect);
                if (currOverhead < minOverhead && !dropbox.overlaps(cRect))
                {
                    minOverhead = currOverhead;
                    minOverheadRect = cRect;
                }
            }
            dropbox.put(minOverheadRect);
        }
        return dropbox;
    }

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
}