package leo.engineCore.engineFoundation.Assert;

import org.apache.log4j.Logger;
import org.testng.Assert;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("unused")
public class TestResult implements Serializable {
    private String expectedValue;
    private String actualValue;
    private String message;

    TestResult(String expectedValue, String actualValue, String message) {
        this.expectedValue = expectedValue;
        this.actualValue = actualValue;
        this.message = message;
    }


    public void Assert() {
        Assert.assertEquals(actualValue, expectedValue, message);
    }

    public void AssertForWeb(Logger logger) {
        try {
            Assert();
        } catch (AssertionError error) {
            logger.info(new HashMap<String, String>() {{
                put("message", error.getMessage());
                put("color", "red");
            }});
            return;
        }
        logger.info((new HashMap<String, String>() {{
            put("message", "Test pass...");
            put("color", "green");
        }}));
    }

    public String getExpectedValue() {
        return expectedValue;
    }

    public void setExpectedValue(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
