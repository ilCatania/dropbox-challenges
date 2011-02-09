package it.gcatania.dropboxchallenges.fileEvents;

import it.gcatania.dropboxchallenges.fileEvents.model.RawEvent;
import it.gcatania.dropboxchallenges.fileEvents.model.RawEventType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author gcatania
 */
public class EventParsingSupport
{

    /**
     * reads a list of events from standard input
     * @return the parsed events
     */
    public static List<RawEvent> prompt()
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int numEvents = 0;
        do
        {
            String numEventsStr = null;
            try
            {
                numEventsStr = in.readLine();
                if (numEventsStr != null)
                {
                    numEvents = Integer.parseInt(numEventsStr);
                }
            }
            catch (IOException e)
            {
                System.err.println(e.getLocalizedMessage());
                return Collections.emptyList();
            }
            catch (NumberFormatException e)
            {
                System.err.println("Not an integer: " + numEventsStr + " (try again)");
            }
        }
        while (numEvents == 0);

        List<RawEvent> output = new ArrayList<RawEvent>(numEvents);
        for (int i = 0; i < numEvents; i++)
        {
            while (true)
            {
                try
                {
                    String eventLine = in.readLine();
                    if (eventLine != null)
                    {
                        String[] eventData = eventLine.split(" ");
                        if (eventData.length == 4)
                        {
                            RawEventType eventType = RawEventType.valueOf(eventData[0]);
                            long timeStamp = Long.parseLong(eventData[1]);
                            String path = eventData[2];
                            String hash = eventData[3];
                            output.add(new RawEvent(eventType, timeStamp, path, hash));
                            break;
                        }
                    }
                }
                catch (IllegalArgumentException e)
                {
                    System.err.println(e.getLocalizedMessage());
                }
                catch (IOException e)
                {
                    System.err.println(e.getLocalizedMessage());
                }
                System.err.println("No rectangle dimensions specified (try again)");
            }
        }

        return output;
    }

    public static List<RawEvent> parse(String filename)
    {
        BufferedReader reader;
        try
        {
            reader = new BufferedReader(new FileReader(filename));
        }
        catch (FileNotFoundException e)
        {
            throw new IllegalArgumentException(e);
        }
        try
        {
            int numEvents = Integer.parseInt(reader.readLine());
            List<RawEvent> output = new ArrayList<RawEvent>(numEvents);
            for (int i = 0; i < numEvents; i++)
            {
                String eventString = reader.readLine();
                String[] eventData = eventString.split(" ");
                if (eventData.length != 4)
                {
                    throw new IllegalArgumentException("Cannot read event data from: " + eventString);
                }

                RawEventType eventType = RawEventType.valueOf(eventData[0]);
                long timeStamp = Long.parseLong(eventData[1]);
                String path = eventData[2];
                String hash = eventData[3];
                output.add(new RawEvent(eventType, timeStamp, path, hash));
            }
            return output;
        }
        catch (IOException e)
        {
            throw new IllegalStateException(e);
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                throw new IllegalStateException(e);
            }
        }
    }
}
