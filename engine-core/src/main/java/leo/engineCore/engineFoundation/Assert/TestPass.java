package leo.engineCore.engineFoundation.Assert;

import org.apache.log4j.Logger;
import org.testng.Assert;

import java.io.Serializable;
import java.util.HashMap;


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

    @Override
    public void AssertForWeb(Logger logger) {
        logger.info((new HashMap<String, String>() {{
            put("message", "Test pass...");
            put("color", "green");
        }}));
    }
}
