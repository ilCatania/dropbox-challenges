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

import java.util.Collections;
import java.util.List;


/**
 * @author gcatania
 */
public class DietDisplayer
{

    /**
     * @param diets
     */
    public void display(List<Diet> diets)
    {
        if (diets.isEmpty())
        {
            System.out.println("no solution");
            return;
        }
        DietItemNameComparator comp = new DietItemNameComparator();
        boolean first = true;
        for (Diet d : diets)
        {
            if (first)
            {
                first = false;
            }
            else
            {
                System.out.println();
            }
            List<DietItem> items = d.getItems();
            Collections.sort(items, comp);
            for (DietItem i : items)
            {
                System.out.println(i.itemName);
            }
        }
    }

}
