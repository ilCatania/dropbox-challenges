package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.StructuredEvent;

import java.util.List;


/**
 * @author gcatania
 */
public class EventTranslation
{

    public static void main(String[] args)
    {
        List<RawEvent> rawEvents = getEvents(args);
        List<StructuredEvent> output = new EventTranslator().translate(rawEvents);
        new EventDisplayer().display(output);
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
