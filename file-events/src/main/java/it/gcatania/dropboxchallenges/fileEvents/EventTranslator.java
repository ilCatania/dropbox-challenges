package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;

import java.util.List;


/**
 * @author gcatania
 */
public class EventTranslator
{

    public static void main(String[] args)
    {
        // TODO
    }

    public static List<RawEvent> getEvents(String[] args)
    {
        EventParsingSupport eps = new EventParsingSupport();
        if (args.length > 0)
        {
            String filename = args[0];
            System.out.println("Parsing: " + filename);
            return eps.parseFile(filename);
        }
        else
        {
            return eps.parseStandardInput();
        }
    }

}
