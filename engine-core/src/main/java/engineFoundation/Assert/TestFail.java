package engineFoundation.Assert;

import com.google.common.base.Throwables;
import org.testng.Assert;

@SuppressWarnings("unused")
public class TestFail extends TestResult {
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
        Assert.fail(Throwables.getStackTraceAsString(e));
    }
}
