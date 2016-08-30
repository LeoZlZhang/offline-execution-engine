import org.testng.Assert;

/**
 * Created by leo_zlzhang on 8/30/2016.
 *
 */
@SuppressWarnings("unused")
public class HelloWorld {

    public void say() {
        System.out.println("hello world");
    }

    public void say1(String string) {
        System.out.println("hello world " + string);
    }

    public void fail() {
        Assert.fail("fail");
    }

    public Object[] june() {
        return new Object[]{"this is ummer"};
    }
}
