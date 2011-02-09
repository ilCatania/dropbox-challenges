package it.gcatania.dropboxchallenges.packingYourDropbox;

import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.ReverseComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Coordinates;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Rectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators.OverheadCalculator;

import java.util.Collections;
import java.util.Comparator;
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
        Collections.sort(rectangles, new ReverseComparator<Rectangle>(rectangleComparator));
        Dropbox dropbox = new Dropbox();

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
}