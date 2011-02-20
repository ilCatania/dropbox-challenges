package it.gcatania.dropboxchallenges.packingYourDropbox;

import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.FakeComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectangleAreaComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectangleMaxSideComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectangleMaxSideThenPerimeterComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectanglePerimeterComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectangleSideRatioComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Rectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators.DropboxAreaOverheadCalculator;
import it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators.DropboxMagicOverheadCalculator;
import it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators.OverheadCalculator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.time.StopWatch;
import org.testng.annotations.Test;


/**
 * dropbox optimization test class. Generates multiple groups of random rectangles and evaluates the performance of
 * different methods. Outputs the results in a csv file
 * @author gcatania
 */
public class DropboxOptimizationTest
{

    private static final int NUM_ITERATIONS = 100;

    private static final int NUM_RECTANGLES = 20;

    private static final int MIN_SIDE_LENGTH = 1;

    private static final int MAX_SIDE_LENGTH = 80;

    private final Map<Setup, Score> optimizationData = new HashMap<Setup, Score>();

    private final Random random = new Random();

    /**
     * a dropbox build setup
     * @author gcatania
     */
    private static final class Setup
    {

        private final Comparator<Rectangle> comparator;

        private final OverheadCalculator< ? > calculator;

        private Setup(Comparator<Rectangle> comparator, OverheadCalculator< ? > calculator)
        {
            this.calculator = calculator;
            this.comparator = comparator;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof Setup)
            {
                Setup other = (Setup) obj;
                // using by-reference equality, won't work outside this class!
                return other.comparator == comparator && other.calculator == calculator;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode()
        {
            // don't care about null safety here
            return 1 + comparator.hashCode() + 13 * calculator.hashCode();
        }

    }

    /**
     * a dropbox build score
     * @author gcatania
     */
    private static final class Score
    {

        /**
         * counts the number of times the setup yielded the best solution
         */
        private int firstPlaces = 0;

        /**
         * counts the number of times a pre-allocation strategy was more successful than the default one
         */
        private int winningPreAllocations = 0;

        /**
         * counts the number of times a pre-allocation strategy performed like the default one
         */
        private int evenPreAllocations = 0;

        /**
         * the total area of built droboxes
         */
        private long totalArea = 0;

        /**
         * the total free space of built droboxes
         */
        private long totalFreeSpace = 0;

        /**
         * the difference between the total area for this setup and the total area of the setup that performed best
         * overall
         */
        private long totalAreaOverhead = 0;

        /**
         * keeps record of time performance
         */
        private final StopWatch watch = new StopWatch();

        /**
         * transient variable used to compare dropboxes at the end of the single iteration
         */
        private Dropbox lastDropBox = null;

    }

    @Test
    public void testOptimization() throws IOException
    {
        random.setSeed(133l); // make test reproductible

        optimizationData.clear();

        @SuppressWarnings("unchecked")
        List<Comparator<Rectangle>> comparators = Arrays.asList(new FakeComparator<Rectangle>(),
        // new RectangleSuperComparator(), // same as RectangleAreaComparator
            new RectangleMaxSideThenPerimeterComparator(),
            new RectangleAreaComparator(),
            new RectangleMaxSideComparator(),
            new RectangleSideRatioComparator(),
            new RectanglePerimeterComparator());
        List<OverheadCalculator< ? >> overheadCalculators = Arrays.<OverheadCalculator< ? >> asList(
            new DropboxAreaOverheadCalculator(),
            // new AreaOverheadCalculator(), // not optimal
            // new DistanceFromOriginOverheadCalculator(), // not optimal
            new DropboxMagicOverheadCalculator() // not optimal
            // new FreeSpaceOverheadCalculator(), // same as DropBoxAreaOverheadCalculator
            // new DropBoxOverheadCalculator() // not optimal
            );

        for (int i = 0; i < NUM_ITERATIONS; i++)
        {
            System.out.println("pass " + i);
            singlePass(comparators, overheadCalculators);
        }
        List<Map.Entry<Setup, Score>> entrySet = new ArrayList<Map.Entry<Setup, Score>>(optimizationData.entrySet());
        Collections.sort(entrySet, new Comparator<Map.Entry<Setup, Score>>()
        {

            /**
             * compares two scores by their number of first places, then by their total area, then by their total free
             * space
             */
            @Override
            public int compare(Map.Entry<Setup, Score> e1, Map.Entry<Setup, Score> e2)
            {
                Score score1 = e1.getValue();
                Score score2 = e2.getValue();
                int firstPlacesDiff = score2.firstPlaces - score1.firstPlaces;
                if (firstPlacesDiff == 0)
                {
                    long totalAreaDiff = score1.totalArea - score2.totalArea;
                    if (totalAreaDiff == 0)
                    {
                        return Long.signum(score1.totalFreeSpace - score2.totalFreeSpace);
                    }
                    return Long.signum(totalAreaDiff);
                }
                return firstPlacesDiff;
            }
        });

        long minArea = Long.MAX_VALUE;
        long minFreeSpace = Long.MAX_VALUE;
        long minTime = Long.MAX_VALUE;
        long minAreaOverhead = Long.MAX_VALUE;
        for (Map.Entry<Setup, Score> e : entrySet)
        {
            Score score = e.getValue();
            if (minArea > score.totalArea)
            {
                minArea = score.totalArea;
            }
            if (minFreeSpace > score.totalFreeSpace)
            {
                minFreeSpace = score.totalFreeSpace;
            }
            if (minAreaOverhead > score.totalAreaOverhead)
            {
                minAreaOverhead = score.totalAreaOverhead;
            }
            long time = score.watch.getTime();
            if (minTime > time)
            {
                minTime = time;
            }
        }

        BufferedWriter w = new BufferedWriter(new FileWriter("target/results.csv", false));
        w.write("Comparator;Calculator;FirstPlaces;FirstPlacesPerc;TotalArea;TotalAreaPerc;"
            + "FreeSpace;FreeSpacePerc;TimeMillis;TimeMillisPerc;AreaOverhead;AreaOverheadPerc;"
            + "winningPreAllocationsPerc;evenPreAllocationsPerc\n");
        for (Map.Entry<Setup, Score> e : entrySet)
        {
            Setup setup = e.getKey();
            Score score = e.getValue();
            int firstPlacesPerc = 100 * score.firstPlaces / NUM_ITERATIONS;
            long totalAreaPerc = 100 * score.totalArea / minArea;
            long freeSpacePerc = 100 * score.totalFreeSpace / minFreeSpace;
            long timePerc = 100 * score.watch.getTime() / minTime;
            long areaOverheadPerc = minAreaOverhead > 0 ? 100 * score.totalAreaOverhead / minAreaOverhead : 0;
            int winningPreAllocationsPerc = score.winningPreAllocations * 100 / NUM_ITERATIONS;
            int evenPreAllocationsPerc = score.evenPreAllocations * 100 / NUM_ITERATIONS;
            w.write(new StringBuilder(setup.comparator.getClass().getSimpleName())
                .append(';')
                .append(setup.calculator.getClass().getSimpleName())
                .append(';')
                .append(score.firstPlaces)
                .append(';')
                .append(firstPlacesPerc)
                .append(';')
                .append(score.totalArea)
                .append(';')
                .append(totalAreaPerc)
                .append(';')
                .append(score.totalFreeSpace)
                .append(';')
                .append(freeSpacePerc)
                .append(';')
                .append(score.watch.getTime())
                .append(';')
                .append(timePerc)
                .append(';')
                .append(score.totalAreaOverhead)
                .append(';')
                .append(areaOverheadPerc)
                .append(';')
                .append(winningPreAllocationsPerc)
                .append(';')
                .append(evenPreAllocationsPerc)
                .append('\n')
                .toString());
        }
        w.close();
    }

    private void singlePass(List<Comparator<Rectangle>> comparators, List<OverheadCalculator< ? >> overheadCalculators)
    {
        List<Rectangle> rectangles = createRandomRectangles();

        long minArea = Long.MAX_VALUE;
        for (Comparator<Rectangle> comp : comparators)
        {
            for (OverheadCalculator< ? > o : overheadCalculators)
            {
                Setup setup = new Setup(comp, o);
                Score score = optimizationData.get(setup);
                if (score == null)
                {
                    score = new Score();
                    optimizationData.put(setup, score);
                    score.watch.start();
                }
                else
                {
                    score.watch.resume();
                }

                DropboxBuilder builder = new DropboxBuilder(comp, o);
                Dropbox dropbox = builder.build(rectangles);
                Dropbox dropbox2 = builder.buildWithPreAllocation(rectangles);
                score.watch.suspend();

                if (dropbox2.getArea() < dropbox.getArea())
                {
                    dropbox = dropbox2;
                    score.winningPreAllocations++;
                }
                else if (dropbox2.getArea() == dropbox.getArea())
                {
                    score.evenPreAllocations++;
                }

                long currentArea = dropbox.getArea();
                score.totalArea += currentArea;
                score.totalFreeSpace += dropbox.getFreeSpace();
                score.lastDropBox = dropbox;
                if (currentArea < minArea)
                {
                    minArea = currentArea;
                }
                // System.out.println(dropbox.draw());
            }
        }

        for (Score score : optimizationData.values())
        {
            Dropbox db = score.lastDropBox;
            long areaOverhead = db.getArea() - minArea;
            if (areaOverhead == 0)
            {
                score.firstPlaces++;
            }
            else
            {
                score.totalAreaOverhead += areaOverhead;
            }
        }
    }

    private List<Rectangle> createRandomRectangles()
    {
        List<Rectangle> rectangles = new ArrayList<Rectangle>(NUM_RECTANGLES);
        for (int i = 0; i < NUM_RECTANGLES; i++)
        {
            int width = MIN_SIDE_LENGTH + Math.abs(random.nextInt() % MAX_SIDE_LENGTH);
            int height = MIN_SIDE_LENGTH + Math.abs(random.nextInt() % MAX_SIDE_LENGTH);
            Rectangle rect = new Rectangle(width, height);
            // System.out.println("Adding random " + rect);
            rectangles.add(rect);
        }
        return rectangles;
    }

}
