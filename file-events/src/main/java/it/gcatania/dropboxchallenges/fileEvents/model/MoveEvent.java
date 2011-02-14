package it.gcatania.dropboxchallenges.fileEvents.model;

/**
 * @author gcatania
 */
public class MoveEvent extends StructuredEvent
{

    public final String fullPathFrom;

    public final String fullPathTo;

    public final String commonPrefix;

    public final String partialFrom;

    public final String partialTo;

    public MoveEvent(RawEvent delEvent, RawEvent addEvent)
    {
        fullPathFrom = delEvent.data.fullPath;
        fullPathTo = addEvent.data.fullPath;
        int firstDiff = indexOfDifference(fullPathFrom, fullPathTo);
        commonPrefix = fullPathFrom.substring(0, firstDiff);
        int nameLength = delEvent.data.name.length();
        partialFrom = fullPathFrom.substring(firstDiff, nameLength);
        partialTo = fullPathTo.substring(firstDiff, nameLength);
    }

    /**
     * copied from commons-lang
     */
    private static int indexOfDifference(String str1, String str2)
    {
        int i;
        for (i = 0; i < str1.length() && i < str2.length(); ++i)
        {
            if (str1.charAt(i) != str2.charAt(i))
            {
                break;
            }
        }
        if (i < str2.length() || i < str1.length())
        {
            return i;
        }
        return -1;
    }

}