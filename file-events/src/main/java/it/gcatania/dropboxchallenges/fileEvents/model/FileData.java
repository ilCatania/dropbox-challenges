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
 * wrapper for file data.
 * @author gcatania
 */
public class FileData extends FileSystemData
{

    /**
     * the file hash.
     */
    public final String hash;

    public FileData(String path, String hash)
    {
        super(path);
        this.hash = hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof FileData && super.equals(obj))
        {
            FileData other = (FileData) obj;
            return hash.equals(other.hash);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return new StringBuilder()
            .append("FileData [fullPath=")
            .append(fullPath)
            .append(", hash=")
            .append(hash)
            .append("]")
            .toString();
    }

}
