package it.gcatania.dropboxchallenges.packingYourDropbox;

import it.gcatania.dropboxchallenges.packingYourDropbox.model.CartesianRectangle;
import it.gcatania.dropboxchallenges.packingYourDropbox.model.Coordinates;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * @author gcatania
 */
public class CartesianRectangleTest
{

    @Test
    public void testRectangle()
    {
        CartesianRectangle cr = new CartesianRectangle(2, 1, 3, 4);
        Assert.assertTrue(cr.isAngle(new Coordinates(2, 1)));
        Assert.assertTrue(cr.isAngle(new Coordinates(5, 5)));
        Assert.assertFalse(cr.isAngle(new Coordinates(4, 5)));
        Assert.assertFalse(cr.isAngle(new Coordinates(5, 4)));
        Assert.assertTrue(cr.isHorizontalPerimeter(new Coordinates(3, 1)));
        Assert.assertTrue(cr.isHorizontalPerimeter(new Coordinates(4, 5)));
        Assert.assertFalse(cr.isHorizontalPerimeter(new Coordinates(5, 2)));
        Assert.assertFalse(cr.isHorizontalPerimeter(new Coordinates(4, 2)));
        Assert.assertTrue(cr.isVerticalPerimeter(new Coordinates(2, 2)));
        Assert.assertTrue(cr.isVerticalPerimeter(new Coordinates(5, 4)));
        Assert.assertFalse(cr.isVerticalPerimeter(new Coordinates(4, 1)));
        Assert.assertFalse(cr.isVerticalPerimeter(new Coordinates(4, 0)));
    }
}
