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
 * @author gcatania
 */
public class Rectangle
{

    protected final int width;

    protected final int height;

    /**
     * @param width the width
     * @param height the height
     */
    public Rectangle(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    /**
     * @return the width
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight()
    {
        return height;
    }

    public int getSemiPerimeter()
    {
        return width + height;
    }

    public int getPerimeter()
    {
        return 2 * getSemiPerimeter();
    }

    public int getArea()
    {
        return width * height;
    }

    /**
     * @return a new rectangle, with height and width swapped
     */
    public Rectangle rotate()
    {
        return new Rectangle(height, width);
    }

    public boolean isSquare()
    {
        return height == width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return (1 + width) << height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Rectangle)
        {
            Rectangle other = (Rectangle) obj;
            return other.width == width && other.height == height;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return new StringBuilder("Rectangle(width: ")
            .append(width)
            .append(", height: ")
            .append(height)
            .append(')')
            .toString();
    }
}
