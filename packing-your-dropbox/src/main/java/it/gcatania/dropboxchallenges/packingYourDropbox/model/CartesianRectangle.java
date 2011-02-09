package it.gcatania.dropboxchallenges.packingYourDropbox.model;

import java.util.Arrays;
import java.util.List;


/**
 * @author gcatania
 */
public class CartesianRectangle extends Rectangle
{

    private final Coordinates lowerLeft;

    private final Coordinates lowerRight;

    private final Coordinates upperRight;

    private final Coordinates upperLeft;

    public CartesianRectangle(Coordinates lowerLeft, Rectangle rectangle)
    {
        super(rectangle.width, rectangle.height);
        this.lowerLeft = lowerLeft;
        long x = lowerLeft.getX();
        long y = lowerLeft.getY();
        lowerRight = new Coordinates(x + width, y);
        upperRight = new Coordinates(x + width, y + height);
        upperLeft = new Coordinates(x, y + height);
    }

    public CartesianRectangle(long lowerLeftX, long lowerLeftY, int width, int height)
    {
        this(new Coordinates(lowerLeftX, lowerLeftY), new Rectangle(width, height));
    }

    public boolean contains(Coordinates point)
    {
        long x = point.getX();
        long y = point.getY();
        return (lowerLeft.getX() <= x)
            && (lowerLeft.getY() <= y)
            && (upperRight.getX() >= x)
            && (upperRight.getY() >= y);
    }

    public boolean perimeterContains(Coordinates point)
    {
        long x = point.getX();
        long y = point.getY();
        if (x == lowerLeft.getX() || x == upperRight.getX())
        {
            return y >= lowerLeft.getY() && y <= upperRight.getY();
        }
        else if (y == lowerLeft.getY() || y == upperRight.getY())
        {
            return x >= lowerLeft.getX() && x <= upperRight.getX();
        }
        return false;
    }

    public boolean isAngle(Coordinates point)
    {
        long x = point.getX();
        long y = point.getY();
        return (x == lowerLeft.getX() || x == upperRight.getX()) && (y == lowerLeft.getY() || y == upperRight.getY());
    }

    public boolean isVerticalPerimeter(Coordinates point)
    {
        long x = point.getX();
        long y = point.getY();
        return (x == lowerLeft.getX() || x == upperRight.getX()) && (y >= lowerLeft.getY() && y <= upperRight.getY());
    }

    public boolean isHorizontalPerimeter(Coordinates point)
    {
        long x = point.getX();
        long y = point.getY();
        return (y == lowerLeft.getY() || y == upperRight.getY()) && (x >= lowerLeft.getX() && x <= upperRight.getX());
    }

    public boolean overlaps(CartesianRectangle other)
    {
        return intersect(lowerLeft.getX(), upperRight.getX(), other.lowerLeft.getX(), other.upperRight.getX())
            && intersect(lowerLeft.getY(), upperRight.getY(), other.lowerLeft.getY(), other.upperRight.getY());
    }

    /**
     * @param s1 the start of the first segment
     * @param e1 the end of the first segment
     * @param s2 the start of the second segment
     * @param e2 the end of the second segment
     * @return true if the intersection between the two segments is a segment, false otherwise
     */
    private static boolean intersect(long s1, long e1, long s2, long e2)
    {
        if (s1 < s2)
        {
            return e1 > s2;
        }
        else
        {
            return e2 > s1;
        }
    }

    /**
     * @return the coordinates of this rectangle's angles, in the cartesian plane, in counter-clockwise order starting
     * from the lower left, i.e.:
     * <ol>
     * <li>lower left</li>
     * <li>lower right</li>
     * <li>upper right</li>
     * <li>upper left</li>
     * </ol>
     */
    public List<Coordinates> getAngleCoordinates()
    {
        return Arrays.asList(lowerLeft, lowerRight, upperRight, upperLeft);
    }

    /**
     * @return the lowerLeft
     */
    public Coordinates getLowerLeft()
    {
        return lowerLeft;
    }

    /**
     * @return the lowerRight
     */
    public Coordinates getLowerRight()
    {
        return lowerRight;
    }

    /**
     * @return the upperRight
     */
    public Coordinates getUpperRight()
    {
        return upperRight;
    }

    /**
     * @return the upperLeft
     */
    public Coordinates getUpperLeft()
    {
        return upperLeft;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return super.hashCode() + 7 * lowerLeft.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        boolean superEquals = super.equals(obj);
        if (obj instanceof CartesianRectangle && superEquals)
        {
            CartesianRectangle other = (CartesianRectangle) obj;
            return other.lowerLeft.equals(lowerLeft);
        }
        return superEquals;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return super.toString() + " at " + lowerLeft.toString();
    }

}