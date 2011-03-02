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
 * thin wrapper for raw filesystem events.
 * @author gcatania
 */
public class RawEvent
{

    /**
     * the hash value that identified directories
     */
    private static String DIRECTORY_HASH = "-";

    public final long timeStamp;

    public final String path;

    public final String hash;

    public final boolean isDirectory;

    public final boolean isAdd;

    public final boolean isDel;

    /**
     * @param type the raw event type
     * @param timeStamp the event time stamp
     * @param path the event path
     */
    public RawEvent(RawEventType type, long timeStamp, String path, String hash)
    {
        this.timeStamp = timeStamp;
        this.path = path;
        this.hash = hash;
        isDirectory = hash.equals(DIRECTORY_HASH);
        isAdd = RawEventType.ADD.equals(type);
        isDel = !isAdd;
    }

    /**
     * shorthand constructor for unit testing
     * @param type the raw event type
     * @param timeStamp the event time stamp
     * @param path the event path
     */
    public RawEvent(RawEventType type, long timeStamp, String path)
    {
        this.timeStamp = timeStamp;
        this.path = path;
        hash = DIRECTORY_HASH = "-";
        isDirectory = true;
        isAdd = RawEventType.ADD.equals(type);
        isDel = !isAdd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return new StringBuilder()
            .append("RawEvent [timeStamp=")
            .append(timeStamp)
            .append(", path=")
            .append(path)
            .append(", hash=")
            .append(hash)
            .append(", isDirectory=")
            .append(isDirectory)
            .append(", isAdd=")
            .append(isAdd)
            .append("]")
            .toString();
    }

}
