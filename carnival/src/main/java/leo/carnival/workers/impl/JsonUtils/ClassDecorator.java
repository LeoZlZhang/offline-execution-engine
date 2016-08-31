package leo.carnival.workers.impl.JsonUtils;

import leo.carnival.workers.prototype.Processor;

/**
 * Created by leozhang on 8/28/16.
 * Decorate a class refer to json string
 */
@SuppressWarnings("WeakerAccess")
public class ClassDecorator<T> implements Processor<String, T> {
    private Class<T> targetClass;

    @Override
    public T process(String jsonString) {
        if (!GsonUtils.isJsonObject(jsonString))
            return null;
        return GsonUtils.fromJsonObject(jsonString, targetClass);
    }

    public static <G> G process(String jsonString, Class<G> cls) {
        return ClassDecorator.build(cls).process(jsonString);
    }

    public static <G extends X, X> G process(X x, Class<G> cls) {
        return ClassDecorator.build(cls).process(GsonUtils.toJson(x));
    }

    @Override
    public T execute(String jsonString) {
        return process(jsonString);
    }

    public ClassDecorator<T> setTargetClass(Class<T> cls) {
        this.targetClass = cls;
        return this;
    }

    public static <T> ClassDecorator<T> build(Class<T> cls) {
        return new ClassDecorator<T>().setTargetClass(cls);
    }
}
