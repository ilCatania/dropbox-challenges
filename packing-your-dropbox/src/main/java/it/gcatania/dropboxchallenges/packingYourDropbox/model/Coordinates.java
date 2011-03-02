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
package it.gcatania.dropboxchallenges.packingYourDropbox.model;

/**
 * a set of coordinates in the cartesian plane.
 * @author gcatania
 */
public class Coordinates
{

    public static final Coordinates ORIGIN = new Coordinates(0, 0);

    private final long x;

    private final long y;

    public Coordinates(long x, long y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x
     */
    public long getX()
    {
        return x;
    }

    /**
     * @return the y
     */
    public long getY()
    {
        return y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return 1 + new Long(x).hashCode() * 7 + new Long(y).hashCode() * 991;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Coordinates)
        {
            Coordinates other = (Coordinates) obj;
            return other.x == x && other.y == y;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return new StringBuilder(8).append('(').append(x).append(", ").append(y).append(')').toString();
    }
}
