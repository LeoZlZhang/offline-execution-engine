package leo.carnival.workers.impl.JsonUtils;

import leo.carnival.workers.baseType.Processor;

import java.io.IOException;

/**
 * Created by leozhang on 8/28/16.
 * Decorate a class refer to json string
 */
public class ClassDecorator<T> implements Processor<String, T> {
    private Class<T> targetClass;

    @Override
    public T process(String jsonString) {
        if (!GsonUtils.isJsonObject(jsonString))
            return null;

        return GsonUtils.fromJsonObject(jsonString, targetClass);
    }

    public T process(String jsonString, Class<T> cls){
        setTargetClass(cls);
        return process(jsonString);
    }

    @Override
    public T execute(String jsonString) {
        return process(jsonString);
    }

    public ClassDecorator<T> setTargetClass(Class<T> cls) {
        this.targetClass = cls;
        return this;
    }
}
