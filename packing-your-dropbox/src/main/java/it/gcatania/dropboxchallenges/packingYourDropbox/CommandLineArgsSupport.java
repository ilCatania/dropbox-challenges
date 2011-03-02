/**
 * Copyright 2011 Gabriele Catania <gabriele.ctn@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package it.gcatania.dropboxchallenges.packingYourDropbox;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
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
        Iterator<String> iter = leftoverArgs.iterator();
        while (iter.hasNext())
        {
            if (arg.equals(iter.next()))
            {
                iter.remove();
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
