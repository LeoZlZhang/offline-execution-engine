package engineFoundation.Assert;

import org.testng.Assert;

public class TestResult
{
    private String expectedValue;
    private String actualValue;
    private String message;

    TestResult(String expectedValue, String actualValue, String message)
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
