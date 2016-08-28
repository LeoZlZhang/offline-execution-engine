package engineFoundation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leozhang on 8/28/16.
 * Container of execution context
 */
public class ApplicationContext {

    private Map<String, Object> context = new HashMap<>();
    private Map<String, Map.Entry<Method, Object>> methodRepo = new HashMap<>();

    public void setContext(Map<String, Object> newContext) {
        this.context.putAll(newContext);
    }

    public Map<String, Object> getContext() {
        return this.context;
    }

    public <T> void setMethodRepo(Class<T> cls) throws IllegalAccessException, InstantiationException {
        T obj = cls.newInstance();
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            methodRepo.put(method.getName(), new AbstractMap.SimpleEntry<>(method, obj));
        }
    }

    public Object[] requestComponent(String methodName, Object[] parameters) throws InvocationTargetException, IllegalAccessException {
        return (Object[]) methodRepo.get(methodName).getKey().invoke(methodRepo.get(methodName).getValue(), parameters);
    }

}
