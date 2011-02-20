/**
 * 
 */
package it.gcatania.dropboxchallenges.packingYourDropbox;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


/**
 * stateful internal utility class to parse command line arguments
 * @author gcatania
 */
public class CommandLineArgsSupport
{

    private final String[] args;

    /**
     * arguments not yet returned
     */
    private final List<String> leftoverArgs;

    public CommandLineArgsSupport(String[] args)
    {
        this.args = args;
        leftoverArgs = new LinkedList<String>(Arrays.asList(args));
    }

    public boolean noArgs()
    {
        return args.length == 0;
    }

    public boolean noMoreArgs()
    {
        return leftoverArgs.isEmpty();
    }

    public boolean hasArg(String arg)
    {
        for (String s : args)
        {
            if (arg.equals(s))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @param qualifier the argument that comes immediately after the one to retrieve (e.g. to retrieve 'file.txt' from
     * '-f file.txt', the qualifier would be '-f')
     * @return the string after the qualifier in the leftover arguments, or null if the qualifier is not found
     * @throws IllegalArgumentException if there is no argument after the qualifier
     */
    public String getArgAfter(String qualifier) throws IllegalArgumentException
    {
        ListIterator<String> iter = leftoverArgs.listIterator();
        while (iter.hasNext())
        {
            if (iter.next().equals(qualifier))
            {
                if (iter.hasNext())
                {
                    iter.remove();
                    String result = iter.next();
                    iter.remove();
                    return result;
                }
                else
                {
                    throw new IllegalArgumentException("Missing argument after \'" + qualifier + "\'.");
                }
            }
        }
        return null;
    }

    /**
     * @return the leftoverArgs
     */
    public List<String> getLeftoverArgs()
    {
        return Collections.unmodifiableList(leftoverArgs);
    }

}
