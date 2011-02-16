package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.structured.StructuredEvent;

import java.text.DateFormat;
import java.util.List;


/**
 * @author gcatania
 */
public class EventDisplayer
{

    public void display(List< ? extends StructuredEvent> events)
    {
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        for (StructuredEvent se : events)
        {
            System.out.println(se.display(df));
        }
    }

}
