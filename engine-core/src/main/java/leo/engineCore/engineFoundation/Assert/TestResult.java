package leo.engineCore.engineFoundation.Assert;

import org.testng.Assert;

import java.io.Serializable;

public class TestResult implements Serializable
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
