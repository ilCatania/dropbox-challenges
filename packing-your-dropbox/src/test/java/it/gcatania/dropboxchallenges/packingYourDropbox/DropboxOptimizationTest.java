package it.gcatania.dropboxchallenges.packingYourDropbox;

import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.FakeComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectangleAreaComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectangleMaxSideComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectanglePerimeterComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectangleSideRatioComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectangleUltraComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Rectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators.DropboxAreaOverheadCalculator;
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


/**
 * @author gcatania
 */
public class DropboxOptimizationTest
{

    private static final int NUM_ITERATIONS = 10;

    private static final int NUM_RECTANGLES = 20;

    private static final int MIN_SIDE_LENGTH = 4;

    private static final int MAX_SIDE_LENGTH = 8;

    private Map<Setup, Score> optimizationData;

    // private StringBuilder samples;

    private static final class Setup
    {

        private final Comparator<Rectangle> comparator;

        private final OverheadCalculator calculator;

        private Setup(Comparator<Rectangle> comparator, OverheadCalculator calculator)
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

    private static final class Score
    {

        private int firstPlaces = 0;

        private int winningPreAllocations = 0;

        private long totalArea = 0;

        private long totalFreeSpace = 0;

        private long totalAreaOverhead = 0;

        private final StopWatch watch = new StopWatch();

        private Dropbox lastDropBox = null;

    }

    public static void main(String[] args) throws Exception
    {
        new DropboxOptimizationTest().testOptimization();
    }

    public void testOptimization() throws IOException
    {
        // 133l
        Random random = new Random();
        random.setSeed(133l);

        optimizationData = new HashMap<Setup, Score>();
        // samples = new StringBuilder();

        @SuppressWarnings("unchecked")
        List<Comparator<Rectangle>> comparators = Arrays.asList(new FakeComparator<Rectangle>(),
        // new RectangleSuperComparator(), // same as RectangleAreaComparator
            new RectangleUltraComparator(),
            new RectangleAreaComparator(),
            new RectangleMaxSideComparator(),
            new RectangleSideRatioComparator(),
            new RectanglePerimeterComparator());
        List<OverheadCalculator> overheadCalculators = Arrays.<OverheadCalculator> asList(
        // new AreaOverheadCalculator(), // not optimal
        // new DistanceFromOriginOverheadCalculator(), // not optimal
            new DropboxAreaOverheadCalculator()
            // new FreeSpaceOverheadCalculator(), // same as DropBoxAreaOverheadCalculator
            // new DropBoxOverheadCalculator() // not optimal
            );

        for (int i = 0; i < NUM_ITERATIONS; i++)
        {
            System.out.println("pass " + i);
            singlePass(random, comparators, overheadCalculators);
        }
        List<Map.Entry<Setup, Score>> entrySet = new ArrayList<Map.Entry<Setup, Score>>(optimizationData.entrySet());
        Collections.sort(entrySet, new Comparator<Map.Entry<Setup, Score>>()
        {

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

        int totalFirstPlaces = 0;
        long minArea = Long.MAX_VALUE;
        long minFreeSpace = Long.MAX_VALUE;
        long minTime = Long.MAX_VALUE;
        long minAreaOverhead = Long.MAX_VALUE;
        for (Map.Entry<Setup, Score> e : entrySet)
        {
            Score score = e.getValue();
            totalFirstPlaces += score.firstPlaces;
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
        w
            .write("Comparator;Calculator;FirstPlaces;FirstPlacesPerc;TotalArea;TotalAreaPerc;FreeSpace;FreeSpacePerc;TimeMillis;TimeMillisPerc;AreaOverhead;AreaOverheadPerc\n");
        for (Map.Entry<Setup, Score> e : entrySet)
        {
            Setup setup = e.getKey();
            Score score = e.getValue();
            int firstPlacesPerc = 100 * score.firstPlaces / totalFirstPlaces;
            long totalAreaPerc = 100 * score.totalArea / minArea;
            long freeSpacePerc = 100 * score.totalFreeSpace / minFreeSpace;
            long timePerc = 100 * score.watch.getTime() / minTime;
            long areaOverheadPerc = 100 * score.totalAreaOverhead / minAreaOverhead;
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
                .append('\n')
                .toString());
            System.out.println(score.winningPreAllocations);
        }
        w.close();
        // System.out.println(samples.toString());
    }

    private void singlePass(Random random, List<Comparator<Rectangle>> comparators,
        List<OverheadCalculator> overheadCalculators)
    {

        List<Rectangle> rectangles = createRandomRectangles(random);

        long minArea = Long.MAX_VALUE;
        for (Comparator<Rectangle> comp : comparators)
        {
            for (OverheadCalculator o : overheadCalculators)
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

                long currentArea = dropbox.getArea();
                score.totalArea += currentArea;
                score.totalFreeSpace += dropbox.getFreeSpace();
                score.lastDropBox = dropbox;
                if (currentArea < minArea)
                {
                    minArea = currentArea;
                }
                System.out.println(dropbox.draw());
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
        // boolean zeroFirstPlaces = false;
        // while (iSetups.hasNext())
        // {
        // Setup setup = iSetups.next();
        // Score score = iScores.next();
        // DropBox box = iBoxes.next();
        // if (score.firstPlaces == 0)
        // {
        // zeroFirstPlaces = true;
        // samples
        // .append(setup.comparator.getClass().getSimpleName())
        // .append('-')
        // .append(setup.calculator.getClass().getSimpleName())
        // .append('\n')
        // .append(box.draw());
        // }
        // }
        // if (zeroFirstPlaces)
        // {
        // samples.append("end sampleset\n");
        // }
    }

    private static List<Rectangle> createRandomRectangles(Random random)
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
