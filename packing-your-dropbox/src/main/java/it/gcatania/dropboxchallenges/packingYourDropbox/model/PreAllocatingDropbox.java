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
