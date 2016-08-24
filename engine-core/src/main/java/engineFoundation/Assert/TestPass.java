package engineFoundation.Assert;

import org.testng.Assert;


public class TestPass extends TestResult
{
    public TestPass()
    {
        super("", "", "Pass");

    }

    @Override
    public void Assert()
    {
        Assert.assertTrue(true);
    }
}
