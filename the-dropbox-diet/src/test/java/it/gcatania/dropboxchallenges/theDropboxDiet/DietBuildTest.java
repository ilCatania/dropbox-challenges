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

import it.gcatania.dropboxchallenges.theDropboxDiet.DietBuild;

import org.testng.annotations.Test;


/**
 * 
 */

/**
 * @author gcatania
 */
public class DietBuildTest
{
    @Test
    public void testSampleInput1()
    {
        DietBuild.main(new String[] { "src/test/resources/sample-input-1.txt" });
    }

    @Test
    public void testSampleInput2()
    {
        DietBuild.main(new String[] { "src/test/resources/sample-input-2.txt" });
    }

}
