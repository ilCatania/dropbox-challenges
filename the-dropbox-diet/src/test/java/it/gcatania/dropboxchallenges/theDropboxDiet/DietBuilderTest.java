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
package it.gcatania.dropboxchallenges.theDropboxDiet;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * @author gcatania
 */
public class DietBuilderTest
{

    private final DietBuilder instance = new DietBuilder();

    @Test
    public void testNoSolution()
    {
        List<DietItem> diets = Arrays.asList(new DietItem("a", 1), new DietItem("b", -2), new DietItem("c", 3));
        List<Diet> result = instance.findPossibleDietsWith(diets);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testSimpleSolution()
    {
        List<DietItem> diets = Arrays.asList(new DietItem("a", 1), new DietItem("b", -1), new DietItem("c", 3));
        List<Diet> result = instance.findPossibleDietsWith(diets);
        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0), new Diet(new DietItem("a", 1), new DietItem("b", -1)));
    }
}
