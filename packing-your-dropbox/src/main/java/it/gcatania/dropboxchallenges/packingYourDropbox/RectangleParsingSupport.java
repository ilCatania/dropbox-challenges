package it.gcatania.dropboxchallenges.packingYourDropbox;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.Rectangle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author gcatania
 */
public class RectangleParsingSupport
{

    private RectangleParsingSupport()
    {
    }

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
                String[] dimStrings = reader.readLine().split(" ");
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
