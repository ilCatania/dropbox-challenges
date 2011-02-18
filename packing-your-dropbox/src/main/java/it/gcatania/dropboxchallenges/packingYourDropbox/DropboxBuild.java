package it.gcatania.dropboxchallenges.packingYourDropbox;

import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectangleMaxSideComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Rectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators.DropboxAreaOverheadCalculator;
import it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators.OverheadCalculator;

import java.util.Comparator;
import java.util.List;


/**
 * @author gcatania
 */
public final class DropboxBuild
{

    /**
     * works best
     */
    private static final DropboxAreaOverheadCalculator DEFAULT_OVERHEAD_CALC = new DropboxAreaOverheadCalculator();

    /**
     * works best when rectangles are far from being squares
     */
    private static final RectangleMaxSideComparator DEFAULT_RECTANGLE_COMP = new RectangleMaxSideComparator();

    private DropboxBuild()
    {
    }

    /**
     * @param args the first argument, if any, will be treated as a file to be parsed for rectangles. Otherwise,
     * standard input will be prompted
     */
    public static void main(String[] args)
    {
        Comparator<Rectangle> rectangleComparator = getComparator(args);
        OverheadCalculator overheadCalculator = getOverheadCalculator(args);

        // System.out.println("Using: " + rectangleComparator.getClass().getSimpleName());
        // System.out.println("Using: " + overheadCalculator.getClass().getSimpleName());

        List<Rectangle> rectangles = getRectangles(args);

        Dropbox built = new DropboxBuilder(rectangleComparator, overheadCalculator).build(rectangles);

        // System.out.println(MessageFormat.format("{0}x{1}", built.getWidth(), built.getHeight()));
        System.out.println(built.getArea());
        System.err.println(built.draw());
    }

    public static List<Rectangle> getRectangles(String[] args)
    {
        RectangleParsingSupport rps = new RectangleParsingSupport();
        if (args.length > 0)
        {
            String filename = args[0];
            System.out.println("Parsing: " + filename);
            return rps.parseFile(filename);
        }
        else
        {
            return rps.parseStandardInput();
        }
    }

    @SuppressWarnings("unchecked")
    public static Comparator<Rectangle> getComparator(String[] args)
    {
        for (String s : args)
        {
            try
            {
                Class< ? > cl = Class.forName(s);
                if (Comparator.class.isAssignableFrom(cl))
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
                if (OverheadCalculator.class.isAssignableFrom(cl))
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
