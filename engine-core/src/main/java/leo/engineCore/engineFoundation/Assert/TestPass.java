package leo.engineCore.engineFoundation.Assert;

import org.testng.Assert;

import java.io.Serializable;


public class TestPass extends TestResult implements Serializable
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
