package it.gcatania.dropboxchallenges.packingYourDropbox.model;

/**
 * @author gcatania
 */
public class PreAllocatingDropbox extends Dropbox
{

    private long preAllocatedWidth;

    private long preAllocatedHeight;

    public void preAllocate(long width, long height)
    {
        preAllocatedWidth = width;
        preAllocatedHeight = height;
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
