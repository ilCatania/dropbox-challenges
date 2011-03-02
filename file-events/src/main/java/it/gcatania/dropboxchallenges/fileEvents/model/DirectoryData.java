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
package it.gcatania.dropboxchallenges.fileEvents.model;

/**
 * wrapper for directory data.
 * @author gcatania
 */
public class DirectoryData extends FileSystemData
{

    public DirectoryData(String path)
    {
        super(path);
    }

    public boolean contains(String path)
    {
        if (path.startsWith(fullPath))
        {
            int myPathLength = fullPath.length();
            if (path.length() == myPathLength)
            {
                return true;
            }
            // must ensure that there is a separator at the end of the containing path. For example, /dir contains
            // /dir/subdir but does not contain /directory
            int minRequiredPathLength = myPathLength + SEPARATOR.length();
            if (path.length() >= minRequiredPathLength)
            {
                return SEPARATOR.equals(path.substring(myPathLength, minRequiredPathLength));
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return new StringBuilder().append("DirectoryData [fullPath=").append(fullPath).append("]").toString();
    }

}
