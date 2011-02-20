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
