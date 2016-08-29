package leo.carnival.workers.implementation.GearicUtils;

/**
 * Created by leo_zlzhang on 8/29/2016.
 * ...
 */
public class ClassUtils {

    public static Class loadClass(String className) {
        if (className == null)
            return null;
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to load class:" + className);
        }
    }

}
