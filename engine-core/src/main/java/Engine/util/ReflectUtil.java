package Engine.util;

import com.vipabc.vliveshow.TestExecutionEngine.Util.Worker.Filter;

import java.lang.reflect.Method;

public class ReflectUtil
{
    public static Method findMethod(Class cls, Filter<Method> filter)
    {
        for(Method m : cls.getMethods()) {
            if(filter.evaluate(m))
                return m;
        }
        return null;
    }

}
