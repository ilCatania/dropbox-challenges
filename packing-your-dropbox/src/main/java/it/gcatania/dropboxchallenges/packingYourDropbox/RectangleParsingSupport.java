package it.gcatania.dropboxchallenges.packingYourDropbox;

import it.gcatania.dropboxchallenges.common.ParsingSupport;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Rectangle;


/**
 * @author gcatania
 */
public class RectangleParsingSupport extends ParsingSupport<Rectangle>
{

    public RectangleParsingSupport()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Rectangle getObject(String objectLine) throws IllegalArgumentException
    {
        String[] dimStrings = objectLine.split(" ");
        if (dimStrings.length != 2)
        {
            throw new IllegalArgumentException("cannot extract dimensions from: " + objectLine);
        }
        int width = Integer.parseInt(dimStrings[0]);
        int height = Integer.parseInt(dimStrings[1]);
        return new Rectangle(width, height);
    }

}
