package leo.engineCore.engineFoundation.Assert;

import org.testng.Assert;

import java.io.Serializable;

@SuppressWarnings("unused")
public class TestFail extends TestResult implements Serializable {
    private Exception e;

    public TestFail(Exception e) {
        super("", "", "");
        this.e = e;
    }

    public TestFail(String message){
        super("","","");
        e = new Exception(message);
    }

    @Override
    public void Assert() {
        StringBuilder sb = new StringBuilder();
        sb.append("Test fail with reason: \r\n");
        e.printStackTrace();
        Assert.fail(sb.append(e.getMessage()).toString());
//        Assert.fail(sb.append(Throwables.getStackTraceAsString(e)).toString());
    }

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }
}
