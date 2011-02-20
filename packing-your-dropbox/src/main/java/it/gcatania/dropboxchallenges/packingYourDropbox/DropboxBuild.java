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

    private static Map<String, Comparator<Rectangle>> COMPARATORS_BY_ID;

    private static Map<String, OverheadCalculator< ? >> CALCULATORS_BY_ID;
    static
    {
        // works best when rectangles are far from being squares (otherwise should use max area comparator)
        RectangleMaxSideComparator defaultComp = new RectangleMaxSideComparator();
        COMPARATORS_BY_ID = new HashMap<String, Comparator<Rectangle>>();
        COMPARATORS_BY_ID.put(null, defaultComp);
        COMPARATORS_BY_ID.put("area", new RectangleAreaComparator());
        COMPARATORS_BY_ID.put("max-side", new RectangleMaxSideComparator());
        COMPARATORS_BY_ID.put("area-max-side", new RectangleAreaThenMaxSideComparator());
        COMPARATORS_BY_ID.put("max-side-perimeter", new RectangleMaxSideThenPerimeterComparator());
        COMPARATORS_BY_ID.put("perimeter", new RectanglePerimeterComparator());
        COMPARATORS_BY_ID.put("side-ratio", new RectangleSideRatioComparator());

        // works best
        DropboxAreaOverheadCalculator defaultCalc = new DropboxAreaOverheadCalculator();
        CALCULATORS_BY_ID = new HashMap<String, OverheadCalculator< ? >>();
        CALCULATORS_BY_ID.put(null, defaultCalc);
        CALCULATORS_BY_ID.put("area", new DropboxAreaOverheadCalculator());
        CALCULATORS_BY_ID.put("free-space", new FreeSpaceOverheadCalculator());
        CALCULATORS_BY_ID.put("magic", new DropboxMagicOverheadCalculator());
    }

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
        boolean bruteForce;
        boolean preAllocate;
        List<Rectangle> rectangles;
        try
        {
            CommandLineArgsSupport clas = new CommandLineArgsSupport(args);
            if (clas.hasArg("-h") || clas.hasArg("-?"))
            {
                showHelp();
                return;
            }

            bruteForce = clas.hasArg("--brute-force");
            preAllocate = clas.hasArg("--pre");

            rectangleComparator = getComparator(clas.getArgAfter("--cmp"));
            overheadCalculator = getOverheadCalculator(clas.getArgAfter("--calc"));
            RectangleParsingSupport rps = new RectangleParsingSupport();
            if (clas.getLeftoverArgs().isEmpty())
            {
                rectangles = rps.parseStandardInput();
            }
            else
            {
                rectangles = rps.parseFile(clas.getLeftoverArgs().get(0));
            }
        }
        catch (IllegalArgumentException e)
        {
            System.err.println(e.getMessage() + " Use -h to display help.");
            return;
        }

        // System.out.println("Using: " + rectangleComparator.getClass().getSimpleName());
        // System.out.println("Using: " + overheadCalculator.getClass().getSimpleName());

        DropboxBuilder builder = new DropboxBuilder(rectangleComparator, overheadCalculator);

        Dropbox built;
        if (bruteForce)
        {
            built = builder.bruteForceBuild(rectangles);
        }
        else if (preAllocate)
        {
            built = builder.buildWithPreAllocation(rectangles);
        }
        else
        {
            built = builder.build(rectangles);
        }

        // System.out.println(MessageFormat.format("{0}x{1}", built.getWidth(), built.getHeight()));
        System.out.println(built.getArea());
        System.err.println(built.draw());
    }

    private static void showHelp()
    {
        System.out.println("Bidimensional dropbox building challenge - usage:");
        System.out.println();
        System.out
            .println("\tThe first argument (except when preceded with --cmp or --calc) will be interpreted as a file to parse.");
        System.out.println("\tUse '--cmp name' to specify a comparator, '--calc name' to specify a calculator,\n"
            + "\t--brute-force to use brute force method and --pre to use pre-allocation method.");
        System.out.println();

        System.out.print("\tAvailable comparators: ");
        boolean first = true;
        for (String str : COMPARATORS_BY_ID.keySet())
        {
            if (str == null)
            {
                continue;
            }
            if (first)
            {
                first = false;
            }
            else
            {
                System.out.print(", ");
            }
            System.out.print(str);
        }
        System.out.println();
        System.out.print("\tAvailable calculators: ");
        first = true;
        for (String str : CALCULATORS_BY_ID.keySet())
        {
            if (str == null)
            {
                continue;
            }
            if (first)
            {
                first = false;
            }
            else
            {
                System.out.print(", ");
            }
            System.out.print(str);
        }
        System.out.println();
        System.out.println("example syntax: java -jar dropboxbuild.jar --cmp area --calc free-space path/to/file.txt");
    }

    private static Comparator<Rectangle> getComparator(String comparatorId)
    {
        Comparator<Rectangle> result = COMPARATORS_BY_ID.get(comparatorId);
        if (result == null)
        {
            throw new IllegalArgumentException("No comparator configured for: \'" + comparatorId + "\'.");
        }
        return result;
    }

    public static OverheadCalculator< ? > getOverheadCalculator(String calculatorId)
    {
        OverheadCalculator< ? > result = CALCULATORS_BY_ID.get(calculatorId);
        if (result == null)
        {
            throw new IllegalArgumentException("No calculator configured for: \'" + calculatorId + "\'.");
        }
        return result;
    }

}
