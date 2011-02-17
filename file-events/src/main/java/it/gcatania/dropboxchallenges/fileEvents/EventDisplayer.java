package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.structured.StructuredEvent;

import java.text.DateFormat;
import java.util.List;


/**
 * standard display helper for structured events.
 * @author gcatania
 */
public class EventDisplayer
{

    /**
     * prints the descriptions for the input structured events to standard output.
     * @param events the events to display
     */
    public void display(List< ? extends StructuredEvent> events)
    {
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        for (StructuredEvent se : events)
        {
            System.out.println(se.display(df));
        }
    }

}
