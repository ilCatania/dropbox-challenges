package it.gcatania.dropboxchallenges.bididropbox;

import it.gcatania.dropboxchallenges.bididropbox.comparators.RectangleAreaComparator;
import it.gcatania.dropboxchallenges.bididropbox.model.DropBox;
import it.gcatania.dropboxchallenges.bididropbox.model.Rectangle;
import it.gcatania.dropboxchallenges.bididropbox.overheadcalculators.DistanceFromOriginOverheadCalculator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author gcatania
 */
public class DropBoxBuild
{

    private static final int RND_NUM_RECTANGLES = 5;

    private static final int RND_MAX_SIDE_LENGTH = 12;

    public static void main(String[] args)
    {
        DropBoxBuild dropBoxBuild = new DropBoxBuild();
        if (args.length > 0)
        {
            dropBoxBuild.run(args[0]);
        }
        else
        {
            dropBoxBuild.runWithRandomInput();
        }
    }

    public void run(String filename)
    {
        List<Rectangle> parsed = RectangleParsingSupport.parse(filename);
        DropBoxBuilder dbb = new DropBoxBuilder(
            new RectangleAreaComparator(),
            new DistanceFromOriginOverheadCalculator());
        execute(dbb, parsed);
    }

    public void runWithRandomInput()
    {
        Random random = new Random();
        random.setSeed(133l);

        List<Rectangle> rectangles = new ArrayList<Rectangle>(RND_NUM_RECTANGLES);
        for (int i = 0; i < RND_NUM_RECTANGLES; i++)
        {
            int width = 1 + Math.abs(random.nextInt() % RND_MAX_SIDE_LENGTH);
            int height = 1 + Math.abs(random.nextInt() % RND_MAX_SIDE_LENGTH);
            Rectangle rect = new Rectangle(width, height);
            System.out.println("Adding random " + rect);
            rectangles.add(rect);
        }
        DropBoxBuilder dbb = new DropBoxBuilder(
            new RectangleAreaComparator(),
            new DistanceFromOriginOverheadCalculator());
        execute(dbb, rectangles);
    }

    private int execute(DropBoxBuilder dbb, List<Rectangle> rectangles)
    {
        DropBox built = dbb.build(rectangles);
        System.out.println(MessageFormat.format("{0}x{1}", built.getWidth(), built.getHeight()));
        System.err.println(built.draw());
        return built.getArea();
    }
}
