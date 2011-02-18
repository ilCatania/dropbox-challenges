package it.gcatania.dropboxchallenges.theDropboxDiet;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * @author gcatania
 */
public class DietBuilderTest
{

    private final DietBuilder instance = new DietBuilder();

    @Test
    public void testNoSolution()
    {
        List<DietItem> diets = Arrays.asList(new DietItem("a", 1), new DietItem("b", -2), new DietItem("c", 3));
        List<Diet> result = instance.findPossibleDietsWith(diets);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testSimpleSolution()
    {
        List<DietItem> diets = Arrays.asList(new DietItem("a", 1), new DietItem("b", -1), new DietItem("c", 3));
        List<Diet> result = instance.findPossibleDietsWith(diets);
        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0), new Diet(new DietItem("a", 1), new DietItem("b", -1)));
    }
}
