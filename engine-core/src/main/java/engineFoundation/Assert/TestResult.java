package engineFoundation.Assert;

import org.testng.Assert;

public class TestResult
{
    protected String expectedValue;
    protected String actualValue;
    protected String message;

    public TestResult(String expectedValue, String actualValue, String message)
    {
        this.expectedValue = expectedValue;
        this.actualValue = actualValue;
        this.message = message;
    }


    public void Assert()
    {
        Assert.assertEquals(actualValue, expectedValue, message);
    }
}
