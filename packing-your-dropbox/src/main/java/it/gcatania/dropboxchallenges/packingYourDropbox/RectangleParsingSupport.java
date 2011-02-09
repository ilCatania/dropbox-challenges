package it.gcatania.dropboxchallenges.packingYourDropbox;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.Rectangle;

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
public final class RectangleParsingSupport
{

    private RectangleParsingSupport()
    {
    }

    /**
     * prompts for a list of rectangle to standard input
     * @return the rectangles
     */
    public static List<Rectangle> prompt()
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int numRectangles = 0;
        do
        {
            String numRectanglesStr = null;
            try
            {
                numRectanglesStr = in.readLine();
                if (numRectanglesStr != null)
                {
                    numRectangles = Integer.parseInt(numRectanglesStr);
                }
            }
            catch (IOException e)
            {
                System.err.println(e.getLocalizedMessage());
                return Collections.emptyList();
            }
            catch (NumberFormatException e)
            {
                System.err.println("Not an integer: " + numRectanglesStr + " (try again)");
            }
        }
        while (numRectangles == 0);

        List<Rectangle> output = new ArrayList<Rectangle>(numRectangles);
        for (int i = 0; i < numRectangles; i++)
        {
            while (true)
            {
                try
                {
                    String rectangles = in.readLine();
                    if (rectangles != null)
                    {
                        String[] splittedRectangles = rectangles.split(" ");
                        if (splittedRectangles.length == 2)
                        {
                            int width = Integer.parseInt(splittedRectangles[0]);
                            int height = Integer.parseInt(splittedRectangles[1]);
                            output.add(new Rectangle(width, height));
                            break;
                        }
                    }
                }
                catch (NumberFormatException e)
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

    /**
     * parses a list of rectangles from an input file
     * @param filename the path of the input file
     * @return the parsed rectangles
     */
    public static List<Rectangle> parse(String filename)
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
            int numRectangles = Integer.parseInt(reader.readLine());
            List<Rectangle> output = new ArrayList<Rectangle>(numRectangles);
            for (int i = 0; i < numRectangles; i++)
            {
                String rectangleData = reader.readLine();
                String[] dimStrings = rectangleData.split(" ");
                if (dimStrings.length != 2)
                {
                    throw new IllegalArgumentException("cannot extract dimensions from: " + rectangleData);
                }
                int width = Integer.parseInt(dimStrings[0]);
                int height = Integer.parseInt(dimStrings[1]);
                output.add(new Rectangle(width, height));
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
