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

import java.util.ArrayList;
import java.util.List;


/**
 * @author gcatania
 */
public class DietBuilder
{

    private static final boolean RETURN_FIRST_RESULT = true;

    /**
     * @param items
     * @return
     */
    public List<Diet> findPossibleDietsWith(List<DietItem> items)
    {
        List<Diet> results = new ArrayList<Diet>();
        List<Diet> combinations = new ArrayList<Diet>();

        for (DietItem item : items)
        {
            List<Diet> dietsToAdd = new ArrayList<Diet>();
            for (Diet d : combinations)
            {
                Diet clone = d.clone();
                clone.add(item);
                if (clone.getCurrentCals() == 0)
                {
                    results.add(clone);
                    if (RETURN_FIRST_RESULT)
                    {
                        return results;
                    }
                }
                dietsToAdd.add(clone);
            }
            Diet singleItemDiet = new Diet();
            singleItemDiet.add(item);
            dietsToAdd.add(singleItemDiet);
            combinations.addAll(dietsToAdd);
        }
        return results;
    }

}
