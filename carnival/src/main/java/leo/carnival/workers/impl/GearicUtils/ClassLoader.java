package leo.carnival.workers.impl.GearicUtils;

import leo.carnival.workers.prototype.Processor;

/**
 * Created by leo_zlzhang on 8/29/2016.
 * ...
 */
public class ClassLoader  implements Processor<String, Class>{

    @Override
    public Class process(String className) {
        if (className == null)
            return null;
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to load class:" + className);
        }
    }

    @Override
    public Class execute(String className) {
        return process(className);
    }
}
