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
 * a dropbox that allows pre-allocation of space
 * @author gcatania
 */
public class PreAllocatingDropbox extends Dropbox
{

    public final long preAllocatedWidth;

    public final long preAllocatedHeight;

    public PreAllocatingDropbox(long preAllocatedWidth, long preAllocatedHeight)
    {
        this.preAllocatedWidth = preAllocatedWidth;
        this.preAllocatedHeight = preAllocatedHeight;
    }

    /**
     * @param other
     */
    public PreAllocatingDropbox(PreAllocatingDropbox other)
    {
        super(other);
        preAllocatedWidth = other.preAllocatedWidth;
        preAllocatedHeight = other.preAllocatedHeight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getWidthWith(CartesianRectangle rect)
    {
        return Math.max(super.getWidthWith(rect), preAllocatedWidth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getHeightWith(CartesianRectangle rect)
    {
        return Math.max(super.getHeightWith(rect), preAllocatedHeight);
    }

}
