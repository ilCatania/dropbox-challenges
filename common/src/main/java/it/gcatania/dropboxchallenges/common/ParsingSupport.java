package it.gcatania.dropboxchallenges.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * base helper class to parse a list of objects either from standard input or from a file, one object per line.
 * @param <T> the type of the model objects parsed by this class
 * @author gcatania
 */
public abstract class ParsingSupport<T>
{

    /**
     * parses an object from an input character string.
     * @param objectLine the line to parse from
     * @return the parsed object
     * @throws IllegalArgumentException if parse fails
     */
    protected abstract T getObject(String objectLine) throws IllegalArgumentException;

    /**
     * parse a list of objects from an input reader, one object per line.
     * @param input the input stream to read from
     * @return the parsed objects
     */
    private List<T> parseObjects(BufferedReader input)
    {
        int numObjects = 0;
        do
        {
            String numObjectsStr = null;
            try
            {
                numObjectsStr = input.readLine();
                if (numObjectsStr != null)
                {
                    numObjects = Integer.parseInt(numObjectsStr);
                }
            }
            catch (IOException e)
            {
                System.err.println(e.getLocalizedMessage());
                return Collections.emptyList();
            }
            catch (NumberFormatException e)
            {
                System.err.println("Not an integer: " + numObjectsStr + " (try again)");
            }
        }
        while (numObjects == 0);

        List<T> output = new ArrayList<T>(numObjects);
        for (int i = 0; i < numObjects; i++)
        {
            while (true)
            {
                try
                {
                    String eventLine = input.readLine();
                    if (eventLine != null)
                    {
                        T ob = getObject(eventLine);
                        output.add(ob);
                        break;
                    }
                }
                catch (IOException e)
                {
                    System.err.println(e.getLocalizedMessage());
                }
                catch (IllegalArgumentException e)
                {
                    System.err.println(e.getLocalizedMessage());
                }
                System.out.println("Try again:");
            }
        }

        return output;
    }

    /**
     * reads a list of model objects from standard input
     * @return the parsed events
     */
    public List<T> parseStandardInput()
    {
        return parseObjects(new BufferedReader(new InputStreamReader(System.in)));
    }

    /**
     * reads a list of model objects from a file
     * @param filename the file name
     * @return the parsed objects
     */
    public List<T> parseFile(String filename)
    {
        BufferedReader reader;
        try
        {
            reader = new BufferedReader(new FileReader(filename));
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getLocalizedMessage());
            return Collections.emptyList();
        }
        try
        {
            return parseObjects(reader);
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
