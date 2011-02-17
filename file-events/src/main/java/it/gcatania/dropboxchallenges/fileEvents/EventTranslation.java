package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.structured.StructuredEvent;

import java.util.List;


/**
 * entry point for command line execution.
 * @author gcatania
 */
public class EventTranslation
{

    /**
     * @param args the first argument, if any, will be parsed for raw events
     */
    public static void main(String[] args)
    {
        List<RawEvent> rawEvents = getEvents(args);
        List<StructuredEvent> output = new EventTranslator().translate(rawEvents);
        new EventDisplayer().display(output);
    }

    /**
     * retrieves the events to process, according to the provided arguments
     * @param args the command line arguments
     * @return the parsed events
     */
    private static List<RawEvent> getEvents(String[] args)
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
