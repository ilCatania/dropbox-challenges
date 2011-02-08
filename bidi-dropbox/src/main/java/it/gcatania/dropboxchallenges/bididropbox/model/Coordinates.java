package it.gcatania.dropboxchallenges.bididropbox.model;

/**
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
        return (int) ((1 + x) << y);
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
