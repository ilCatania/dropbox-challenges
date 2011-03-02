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
