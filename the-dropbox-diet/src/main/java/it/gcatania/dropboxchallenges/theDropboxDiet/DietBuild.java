/**
 * 
 */
package it.gcatania.dropboxchallenges.theDropboxDiet;

import java.util.List;


/**
 * command line entry point.
 * @author gcatania
 */
public class DietBuild
{

    /**
     * @param args the first argument, if any, will be treated as a file to be parsed for diet items. Otherwise,
     * standard input will be prompted
     */
    public static void main(String[] args)
    {
        List<DietItem> items = getItems(args);
        List<Diet> diets = new DietBuilder().findPossibleDietsWith(items);
        new DietDisplayer().display(diets);
    }

    /**
     * retrieves the diet items to process, according to the provided arguments
     * @param args the command line arguments
     * @return the parsed diet items
     */
    private static List<DietItem> getItems(String[] args)
    {
        DietItemParsingSupport eps = new DietItemParsingSupport();
        if (args.length > 0)
        {
            String filename = args[0];
            System.out.println("Parsing: " + filename);
            return eps.parseFile(filename);
        }
        else
        {
            return eps.parseStandardInput();
        }
    }

}
