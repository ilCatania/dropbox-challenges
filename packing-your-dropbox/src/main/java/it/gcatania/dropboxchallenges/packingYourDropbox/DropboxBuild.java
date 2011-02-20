package it.gcatania.dropboxchallenges.packingYourDropbox;

import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectangleAreaComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectangleAreaThenMaxSideComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectangleMaxSideComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectangleMaxSideThenPerimeterComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectanglePerimeterComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.comparators.RectangleSideRatioComparator;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Dropbox;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Rectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators.DropboxAreaOverheadCalculator;
import it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators.DropboxMagicOverheadCalculator;
import it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators.FreeSpaceOverheadCalculator;
import it.gcatania.dropboxchallenges.packingYourDropbox.overheadcalculators.OverheadCalculator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * entry point for command line execution.
 * @author gcatania
 */
public final class DropboxBuild
{

    /**
     * works best
     */
    private static final DropboxAreaOverheadCalculator DEFAULT_OVERHEAD_CALC = new DropboxAreaOverheadCalculator();

    /**
     * works best when rectangles are far from being squares (otherwise should use max area comparator)
     */
    private static final RectangleMaxSideComparator DEFAULT_RECTANGLE_COMP = new RectangleMaxSideComparator();

    private DropboxBuild()
    {
    }

    /**
     * @param args the first argument, if any, will be treated as a file to be parsed for rectangles. Otherwise,
     * standard input will be prompted. For additional options, use '-?' or '-h'.
     */
    public static void main(String[] args)
    {
        Comparator<Rectangle> rectangleComparator;
        OverheadCalculator< ? > overheadCalculator;
        List<Rectangle> rectangles;
        try
        {
            CommandLineArgsSupport as = new CommandLineArgsSupport(args);
            if (as.hasArg("-h") || as.hasArg(" - "))
            {
                showHelp();
                return;
            }

            rectangleComparator = getComparator(as.getArgAfter("--cmp"));
            overheadCalculator = getOverheadCalculator(as.getArgAfter("--calc"));
            RectangleParsingSupport rps = new RectangleParsingSupport();
            if (as.getLeftoverArgs().isEmpty())
            {
                rectangles = rps.parseStandardInput();
            }
            else
            {
                rectangles = rps.parseFile(as.getLeftoverArgs().get(0));
            }
        }
        catch (IllegalArgumentException e)
        {
            System.err.println(e.getMessage());
            return;
        }

        // System.out.println("Using: " + rectangleComparator.getClass().getSimpleName());
        // System.out.println("Using: " + overheadCalculator.getClass().getSimpleName());

        Dropbox built = new DropboxBuilder(rectangleComparator, overheadCalculator).build(rectangles);

        // System.out.println(MessageFormat.format("{0}x{1}", built.getWidth(), built.getHeight()));
        System.out.println(built.getArea());
        System.err.println(built.draw());
    }

    private static void showHelp()
    {
        System.out
            .println("the first argument (except when preceded with --cmp or --calc) will be interpreted as a file to parse");
        System.out.println("use --cmp name to specify a comparator, and --calc name to specify a calculator.");
        System.out
            .println("available comparators: area, max-side, area-max-side, max-side-perimeter, perimeter, side-ratio");
        System.out.println("example syntax: java -jar dropboxbuild.jar --cmp area --calc free-space path/to/file.txt");
    }

    private static Comparator<Rectangle> getComparator(String comparatorId)
    {
        Map<String, Comparator<Rectangle>> comparatorsById = new HashMap<String, Comparator<Rectangle>>();
        comparatorsById.put(null, DEFAULT_RECTANGLE_COMP);
        comparatorsById.put("area", new RectangleAreaComparator());
        comparatorsById.put("max-side", new RectangleMaxSideComparator());
        comparatorsById.put("area-max-side", new RectangleAreaThenMaxSideComparator());
        comparatorsById.put("max-side-perimeter", new RectangleMaxSideThenPerimeterComparator());
        comparatorsById.put("perimeter", new RectanglePerimeterComparator());
        comparatorsById.put("side-ratio", new RectangleSideRatioComparator());

        Comparator<Rectangle> result = comparatorsById.get(comparatorId);
        if (result == null)
        {
            throw new IllegalArgumentException("No comparator configured for: \'" + comparatorId + "\'.");
        }
        return result;
    }

    public static OverheadCalculator< ? > getOverheadCalculator(String calculatorId)
    {
        Map<String, OverheadCalculator< ? >> calculatorsById = new HashMap<String, OverheadCalculator< ? >>();
        calculatorsById.put(null, DEFAULT_OVERHEAD_CALC);
        calculatorsById.put("area", new DropboxAreaOverheadCalculator());
        calculatorsById.put("free-space", new FreeSpaceOverheadCalculator());
        calculatorsById.put("magic", new DropboxMagicOverheadCalculator());

        OverheadCalculator< ? > result = calculatorsById.get(calculatorId);
        if (result == null)
        {
            throw new IllegalArgumentException("No calculator configured for: \'" + calculatorId + "\'.");
        }
        return result;
    }

}
