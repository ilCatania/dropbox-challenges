package it.gcatania.dropboxchallenges.theDropboxDiet;

import it.gcatania.dropboxchallenges.common.ParsingSupport;


/**
 * 
 */

/**
 * @author gcatania
 */
public class DietItemParsingSupport extends ParsingSupport<DietItem>
{

    /**
     * {@inheritDoc}
     */
    @Override
    protected DietItem getObject(String objectLine) throws IllegalArgumentException
    {
        String[] itemStrings = objectLine.split(" ");
        if (itemStrings.length != 2)
        {
            throw new IllegalArgumentException("cannot extract diet item from: " + objectLine);
        }
        // TODO Auto-generated method stub
        return null;
    }

}
