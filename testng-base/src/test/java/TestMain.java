import leo.engineCore.testEngine.TestEngine;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Created by leo_zlzhang on 8/30/2016.
 * ...
 */
public class TestMain {

    @Test
    public void testBasic() {
        File file = new File(this.getClass().getResource("gear.json").getFile());
        TestEngine testEngine = TestEngine.build(file);
        testEngine.execute("testFlow").Assert();
        testEngine.execute("testStringParam").Assert();

    }

    @Test
    public void testFail() {
        File file = new File(this.getClass().getResource("gear.json").getFile());
        TestEngine testEngine = TestEngine.build(file);
        testEngine.execute("assertFail");
    }

    @Test
    public void testTransferParam() {
        File file = new File(this.getClass().getResource("gear.json").getFile());
        TestEngine testEngine = TestEngine.build(file);
        testEngine.execute("testParam").Assert();
    }
}
