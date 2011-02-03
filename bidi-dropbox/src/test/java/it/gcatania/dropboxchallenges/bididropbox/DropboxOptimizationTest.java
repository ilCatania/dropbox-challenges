package it.gcatania.dropboxchallenges.bididropbox;

import it.gcatania.dropboxchallenges.bididropbox.comparators.DropBoxAreaComparator;
import it.gcatania.dropboxchallenges.bididropbox.comparators.RectangleAreaComparator;
import it.gcatania.dropboxchallenges.bididropbox.comparators.RectangleMaxSideComparator;
import it.gcatania.dropboxchallenges.bididropbox.comparators.RectanglePerimeterComparator;
import it.gcatania.dropboxchallenges.bididropbox.model.DropBox;
import it.gcatania.dropboxchallenges.bididropbox.model.Rectangle;
import it.gcatania.dropboxchallenges.bididropbox.overheadcalculators.AreaOverheadCalculator;
import it.gcatania.dropboxchallenges.bididropbox.overheadcalculators.DistanceFromOriginOverheadCalculator;
import it.gcatania.dropboxchallenges.bididropbox.overheadcalculators.DropBoxAreaOverheadCalculator;
import it.gcatania.dropboxchallenges.bididropbox.overheadcalculators.DropBoxOverheadCalculator;
import it.gcatania.dropboxchallenges.bididropbox.overheadcalculators.FreeSpaceOverheadCalculator;
import it.gcatania.dropboxchallenges.bididropbox.overheadcalculators.OverheadCalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.Test;


/**
 * @author gcatania
 */
public class DropboxOptimizationTest
{

    private static final int NUM_ITERATIONS = 100;

    private static final int NUM_RECTANGLES = 20;

    private static final int MIN_SIDE_LENGTH = 3;

    private static final int MAX_SIDE_LENGTH = 18;

    @Test
    public void testOptimization()
    {
        // 133l
        Random random = new Random();
        random.setSeed(133l);

        Map<String, Integer> results = new HashMap<String, Integer>();
        for (int i = 0; i < NUM_ITERATIONS; i++)
        {
            @SuppressWarnings("unchecked")
            List<String> bestStrategies = singlePass(random, Arrays.asList(
                new RectangleAreaComparator(),
                new RectangleMaxSideComparator(),
                new RectanglePerimeterComparator()), Arrays.asList(
                new AreaOverheadCalculator(),
                new DistanceFromOriginOverheadCalculator(),
                new DropBoxAreaOverheadCalculator(),
                new FreeSpaceOverheadCalculator(),
                new DropBoxOverheadCalculator()));
            for (String best : bestStrategies)
            {
                Integer count = results.get(best);
                if (count == null)
                {
                    count = 1;
                }
                else
                {
                    count = count + 1;
                }
                results.put(best, count);
            }
        }
        System.out.println("results:\n");
        for (Map.Entry<String, Integer> e : results.entrySet())
        {
            System.out.println(e.getKey() + ": " + e.getValue());
        }
    }

    private List<String> singlePass(Random random, List<Comparator<Rectangle>> comparators,
        List<OverheadCalculator> overheadCalculators)
    {

        List<Rectangle> rectangles = new ArrayList<Rectangle>(NUM_RECTANGLES);
        for (int i = 0; i < NUM_RECTANGLES; i++)
        {
            int width = MIN_SIDE_LENGTH + Math.abs(random.nextInt() % MAX_SIDE_LENGTH);
            int height = MIN_SIDE_LENGTH + Math.abs(random.nextInt() % MAX_SIDE_LENGTH);
            Rectangle rect = new Rectangle(width, height);
            System.out.println("Adding random " + rect);
            rectangles.add(rect);
        }

        Map<DropBox, String> dropBoxesToUsed = new HashMap<DropBox, String>(comparators.size()
            * overheadCalculators.size(), 1f);
        for (Comparator<Rectangle> comp : comparators)
        {
            for (OverheadCalculator o : overheadCalculators)
            {
                DropBoxBuilder dbb = new DropBoxBuilder(comp, o);
                DropBox result = dbb.build(rectangles);
                String toolsUsed = comp.getClass().getSimpleName() + ";" + o.getClass().getSimpleName();
                dropBoxesToUsed.put(result, toolsUsed);
            }
        }
        List<DropBox> dropBoxes = new ArrayList<DropBox>(dropBoxesToUsed.keySet());
        Collections.sort(dropBoxes, new DropBoxAreaComparator());
        DropBox best = dropBoxes.get(0);
        int bestArea = best.getArea();
        List<String> result = new ArrayList<String>();
        for (DropBox db : dropBoxes)
        {
            if (db.getArea() == bestArea)
            {
                result.add(dropBoxesToUsed.get(db));
            }
        }
        return result;
    }

}
