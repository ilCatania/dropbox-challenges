package it.gcatania.dropboxchallenges.bididropbox;

import it.gcatania.dropboxchallenges.bididropbox.comparators.RectangleAreaComparator;
import it.gcatania.dropboxchallenges.bididropbox.model.DropBox;
import it.gcatania.dropboxchallenges.bididropbox.model.Rectangle;
import it.gcatania.dropboxchallenges.bididropbox.overheadcalculators.DistanceFromOriginOverheadCalculator;
import it.gcatania.dropboxchallenges.bididropbox.overheadcalculators.OverheadCalculator;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;


/**
 * @author gcatania
 */
public class DropBoxBuild
{

    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            // TODO display syntax
            return;
        }
        String filename = getFileName(args);
        Comparator<Rectangle> rectangleComparator = getComparator(args);
        OverheadCalculator overheadCalculator = getOverheadCalculator(args);

        List<Rectangle> parsed = RectangleParsingSupport.parse(filename);

        DropBox built = new DropBoxBuilder(rectangleComparator, overheadCalculator).build(parsed);

        System.out.println(MessageFormat.format("{0}x{1}", built.getWidth(), built.getHeight()));
        System.err.println(built.draw());
    }

    public static String getFileName(String[] args)
    {
        return args[0];
    }

    public static Comparator<Rectangle> getComparator(String[] args)
    {
        return new RectangleAreaComparator();
    }

    public static OverheadCalculator getOverheadCalculator(String[] args)
    {
        return new DistanceFromOriginOverheadCalculator();
    }

}
