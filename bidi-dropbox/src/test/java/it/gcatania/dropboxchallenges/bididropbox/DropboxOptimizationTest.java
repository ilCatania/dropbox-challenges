package it.gcatania.dropboxchallenges.bididropbox;

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

import java.io.IOException;
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

    private static final int NUM_ITERATIONS = 1080;

    private static final int NUM_RECTANGLES = 200;

    private static final int MIN_SIDE_LENGTH = 1;

    private static final int MAX_SIDE_LENGTH = 30;

    private Map<Setup, Score> optimizationData;

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

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString()
        {
            return new StringBuilder(comparator.getClass().getSimpleName())
                .append(' ')
                .append(calculator.getClass().getSimpleName())
                .toString();
        }
    }

    private static final class Score
    {

        private int firstPlaces = 0;

        private int totalArea = 0;

        private int totalFreeSpace = 0;

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString()
        {
            return new StringBuilder("First places: ")
                .append(firstPlaces)
                .append("; total area: ")
                .append(totalArea)
                .append("; total free space: ")
                .append(totalFreeSpace)
                .toString();
        }
    }

    @Test
    public void testOptimization() throws IOException
    {
        // 133l
        Random random = new Random();
        random.setSeed(133l);

        @SuppressWarnings("unchecked")
        List<Comparator<Rectangle>> comparators = Arrays.asList(
            new RectangleAreaComparator(),
            new RectangleMaxSideComparator(),
            new RectanglePerimeterComparator());
        List<OverheadCalculator> overheadCalculators = Arrays.asList(
            new AreaOverheadCalculator(),
            new DistanceFromOriginOverheadCalculator(),
            new DropBoxAreaOverheadCalculator(),
            new FreeSpaceOverheadCalculator(),
            new DropBoxOverheadCalculator());
        optimizationData = new HashMap<Setup, Score>();
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
                    int totalAreaDiff = score1.totalArea - score2.totalArea;
                    if (totalAreaDiff == 0)
                    {
                        return score1.totalFreeSpace - score2.totalFreeSpace;
                    }
                    return totalAreaDiff;
                }
                return firstPlacesDiff;
            }
        });

        int totalFirstPlaces = 0;
        int minArea = Integer.MAX_VALUE;
        int maxFreeSpace = 0;
        for (Map.Entry<Setup, Score> e : entrySet)
        {
            Score score = e.getValue();
            totalFirstPlaces += score.firstPlaces;
            if (minArea > score.totalArea)
            {
                minArea = score.totalArea;
            }
            if (maxFreeSpace < score.totalFreeSpace)
            {
                maxFreeSpace = score.totalFreeSpace;
            }
        }

        System.out
            .println("Comparator;Calculator;FirstPlaces;FirstPlacesPerc;TotalArea;TotalAreaPerc;FreeSpace;FreeSpacePerc");
        for (Map.Entry<Setup, Score> e : entrySet)
        {
            Setup setup = e.getKey();
            Score score = e.getValue();
            int firstPlacesPerc = 100 * score.firstPlaces / totalFirstPlaces;
            int totalAreaPerc = 100 * score.totalArea / minArea;
            int freeSpacePerc = 100 * score.totalFreeSpace / maxFreeSpace;
            System.out.println(new StringBuilder(setup.comparator.getClass().getSimpleName())
                .append(';')
                .append(setup.calculator.getClass().getSimpleName())
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
                .toString());
        }
    }

    private void singlePass(Random random, List<Comparator<Rectangle>> comparators,
        List<OverheadCalculator> overheadCalculators)
    {

        List<Rectangle> rectangles = createRandomRectangles(random);

        int minArea = Integer.MAX_VALUE;
        List<Score> minAreaScores = new ArrayList<Score>();
        for (Comparator<Rectangle> comp : comparators)
        {
            for (OverheadCalculator o : overheadCalculators)
            {
                DropBoxBuilder builder = new DropBoxBuilder(comp, o);
                DropBox dropBox = builder.build(rectangles);
                int currentArea = dropBox.getArea();

                Score score = optimizationData.get(new Setup(comp, o));
                if (score == null)
                {
                    score = new Score();
                    optimizationData.put(new Setup(comp, o), score);
                }
                score.totalArea += currentArea;
                score.totalFreeSpace += dropBox.getFreeSpace();
                if (currentArea < minArea)
                {
                    minArea = currentArea;
                    minAreaScores.clear();
                    minAreaScores.add(score);
                }
                else if (currentArea == minArea)
                {
                    minAreaScores.add(score);
                }
            }
        }
        for (Score score : minAreaScores)
        {
            score.firstPlaces++;
        }
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
