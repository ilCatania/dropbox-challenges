package it.gcatania.dropboxchallenges.fileEvents.model.structured;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;


/**
 * Base class in the hierarchy of structured events.
 * @author gcatania
 */
public abstract class StructuredEvent
{

    public final long timeStamp;

    public StructuredEvent(long timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return new Long(timeStamp).hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof StructuredEvent)
        {
            StructuredEvent other = (StructuredEvent) obj;
            return other.timeStamp == timeStamp;
        }
        return false;
    }

    public abstract String display(DateFormat df);

    protected final String fmt(MessageFormat format, Object... args)
    {
        return format.format(args);
    }

    protected final String tsFmt(DateFormat df)
    {
        return df.format(new Date(timeStamp));
    }

}
