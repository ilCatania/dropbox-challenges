package it.gcatania.dropboxchallenges.bididropbox;

import it.gcatania.dropboxchallenges.bididropbox.comparators.ReverseComparator;
import it.gcatania.dropboxchallenges.bididropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.bididropbox.model.Coordinates;
import it.gcatania.dropboxchallenges.bididropbox.model.DropBox;
import it.gcatania.dropboxchallenges.bididropbox.model.Rectangle;
import it.gcatania.dropboxchallenges.bididropbox.overheadcalculators.OverheadCalculator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;


/**
 * @author gcatania
 */
public class DropBoxBuilder
{

    private final Comparator<Rectangle> rectangleComparator;

    private final OverheadCalculator overheadCalculator;

    public DropBoxBuilder(Comparator<Rectangle> rectangleComparator, OverheadCalculator overheadCalculator)
    {
        this.rectangleComparator = rectangleComparator;
        this.overheadCalculator = overheadCalculator;
    }

    public DropBox build(List<Rectangle> rectangles)
    {
        Collections.sort(rectangles, new ReverseComparator<Rectangle>(rectangleComparator));
        DropBox dropBox = new DropBox();

        for (Rectangle rect : rectangles)
        {
            Set<Coordinates> availableStartingPoints = dropBox.getAvailableStartingPoints();
            if (availableStartingPoints.size() == 1)
            {
                dropBox.put(new CartesianRectangle(availableStartingPoints.iterator().next(), rect));
                continue;
            }

            long minOverhead = Long.MAX_VALUE;
            CartesianRectangle minOverheadRect = null;
            for (Coordinates coords : availableStartingPoints)
            {
                CartesianRectangle cRect = new CartesianRectangle(coords, rect);
                long currOverhead = overheadCalculator.getOverhead(dropBox, cRect);
                if (currOverhead < minOverhead && !dropBox.overlaps(cRect))
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
                currOverhead = overheadCalculator.getOverhead(dropBox, cRect);
                if (currOverhead < minOverhead && !dropBox.overlaps(cRect))
                {
                    minOverhead = currOverhead;
                    minOverheadRect = cRect;
                }
            }
            dropBox.put(minOverheadRect);
        }
        return dropBox;
    }
}