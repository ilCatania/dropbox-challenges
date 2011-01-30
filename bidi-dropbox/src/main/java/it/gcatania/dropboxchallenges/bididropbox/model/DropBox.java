package it.gcatania.dropboxchallenges.bididropbox.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author gcatania
 */
public class DropBox
{

    private final List<CartesianRectangle> addedRectangles;

    private final Set<Coordinates> availableStartingPoints;

    private final Set<Coordinates> upperRightAngles;

    private int height;

    private int width;

    public DropBox()
    {
        addedRectangles = new ArrayList<CartesianRectangle>();
        availableStartingPoints = new HashSet<Coordinates>();
        availableStartingPoints.add(Coordinates.ORIGIN);
        upperRightAngles = new HashSet<Coordinates>();
    }

    private void addIfNotUpperRight(Coordinates coords)
    {
        if (!upperRightAngles.contains(coords))
        {
            availableStartingPoints.add(coords);
        }
    }

    public void put(CartesianRectangle rectangle)
    {
        Coordinates coordinates = rectangle.getLowerLeft();
        boolean removed = availableStartingPoints.remove(coordinates);
        if (!removed)
        {
            throw new IllegalArgumentException("Cannot add " + rectangle);
        }
        addedRectangles.add(rectangle);

        addIfNotUpperRight(rectangle.getLowerRight());
        addIfNotUpperRight(rectangle.getUpperLeft());

        // upper right is never a starting point
        Coordinates upperRight = rectangle.getUpperRight();
        upperRightAngles.add(upperRight);
        width = Math.max(width, upperRight.getX());
        height = Math.max(height, upperRight.getY());
    }

    public boolean overlaps(CartesianRectangle rect)
    {
        for (CartesianRectangle existing : addedRectangles)
        {
            if (existing.overlaps(rect))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the availableStartingPoints
     */
    public Set<Coordinates> getAvailableStartingPoints()
    {
        return availableStartingPoints;
    }

    /**
     * @return the height
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * @return the width
     */
    public int getWidth()
    {
        return width;
    }

    public int getArea()
    {
        return width * height;
    }

    private static enum PointType {
        ANGLE, HORIZONTAL, VERTICAL, SPACE;
    }

    /**
     * quick and dirty drawing method. will review it later if I have time and brain juice
     * @return a graphic representation of the drop box
     */
    public String draw()
    {
        StringBuilder sb = new StringBuilder(6 + getArea()).append(width).append('x').append(height).append(":\n");
        for (int row = 0; row < height; row++)
        {
            for (int column = 0; column < width; column++)
            {
                PointType pt = PointType.SPACE;
                Coordinates current = new Coordinates(column, row);
                Coordinates nextInColumn = new Coordinates(column + 1, row);
                Coordinates nextInRow = new Coordinates(column, row + 1);
                Coordinates nextInBoth = new Coordinates(column + 1, row + 1);
                for (CartesianRectangle cr : addedRectangles)
                {
                    if (!cr.contains(current)
                        || !cr.contains(nextInRow)
                        || !cr.contains(nextInColumn)
                        || !cr.contains(nextInBoth))
                    {
                        continue;
                    }
                    else if (cr.getLowerLeft().equals(current)
                        || cr.getLowerRight().equals(nextInColumn)
                        || cr.getUpperRight().equals(nextInBoth)
                        || cr.getUpperLeft().equals(nextInRow))
                    {
                        pt = PointType.ANGLE;
                        break;
                    }
                    else if (cr.isHorizontalPerimeter(current) || cr.isHorizontalPerimeter(nextInRow))
                    {
                        pt = PointType.HORIZONTAL;
                    }
                    else if (cr.isVerticalPerimeter(current) || cr.isVerticalPerimeter(nextInColumn))
                    {
                        pt = PointType.VERTICAL;
                    }
                }
                char ch;
                switch (pt)
                {
                    case ANGLE :
                        ch = '+';
                        break;
                    case HORIZONTAL :
                        ch = '-';
                        break;
                    case VERTICAL :
                        ch = '|';
                        break;
                    case SPACE :
                    default :
                        ch = ' ';
                        break;
                }
                sb.append(ch);
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
