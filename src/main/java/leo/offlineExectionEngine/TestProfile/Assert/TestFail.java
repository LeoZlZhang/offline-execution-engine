package leo.offlineExectionEngine.TestProfile.Assert;

import org.testng.Assert;

public class TestFail extends TestResult {
    private Exception e;

    public TestFail(Exception e) {
        super("", "", "");
        this.e = e;
    }

    public TestFail(String message){
        super("","",message);

    }

    @Override
    public void Assert() {
        try {
            Thread.sleep(100);
        e.getCause().printStackTrace();
            Thread.sleep(10);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        Assert.fail("");
    }
}
