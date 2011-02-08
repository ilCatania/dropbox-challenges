package it.gcatania.dropboxchallenges.bididropbox;

import it.gcatania.dropboxchallenges.bididropbox.comparators.RectangleMaxSideComparator;
import it.gcatania.dropboxchallenges.bididropbox.model.DropBox;
import it.gcatania.dropboxchallenges.bididropbox.model.Rectangle;
import it.gcatania.dropboxchallenges.bididropbox.overheadcalculators.DropBoxAreaOverheadCalculator;
import it.gcatania.dropboxchallenges.bididropbox.overheadcalculators.OverheadCalculator;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;


/**
 * @author gcatania
 */
public class DropBoxBuild
{

    /**
     * works best
     */
    private static final DropBoxAreaOverheadCalculator DEFAULT_OVERHEAD_CALC = new DropBoxAreaOverheadCalculator();

    /**
     * works best when rectangles are far from being squares
     */
    private static final RectangleMaxSideComparator DEFAULT_RECTANGLE_COMP = new RectangleMaxSideComparator();

    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            System.out.println("usage:\n DropBoxBuild filename [comparatorClass] [overheadCalculatorClass]");
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

    @SuppressWarnings("unchecked")
    public static Comparator<Rectangle> getComparator(String[] args)
    {
        for (String s : args)
        {
            try
            {
                Class< ? > cl = Class.forName(s);
                if (cl.isAssignableFrom(Comparator.class))
                {
                    return (Comparator<Rectangle>) cl.newInstance();
                }
            }
            catch (Exception e)
            {
                // ignore
            }
        }
        return DEFAULT_RECTANGLE_COMP;
    }

    public static OverheadCalculator getOverheadCalculator(String[] args)
    {
        for (String s : args)
        {
            try
            {
                Class< ? > cl = Class.forName(s);
                if (cl.isAssignableFrom(OverheadCalculator.class))
                {
                    return (OverheadCalculator) cl.newInstance();
                }
            }
            catch (Exception e)
            {
                // ignore
            }
        }
        return DEFAULT_OVERHEAD_CALC;
    }

}
