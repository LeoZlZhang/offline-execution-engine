package leo.carnival.workers.implementation.GearicUtils;

import leo.carnival.workers.implementation.JsonUtils.GsonUtils;
import leo.carnival.workers.prototype.Processor;


/**
 * Created by leo_zlzhang on 8/29/2016.
 * Deep clone
 */
public class DeepCloner<T> implements Processor<T, T> {
    private Class<T> cls;

    @Override
    public T process(T t) {
        return GsonUtils.fromJsonObject(GsonUtils.toJson(t), cls);
    }

    public static <G> G process(G g, Class<G> cls) {
        return DeepCloner.build(cls).process(g);
    }

    @Override
    public T execute(T t) {
        return process(t);
    }


    public DeepCloner<T> setClass(Class<T> cls) {
        this.cls = cls;
        return this;
    }

    public static <T> DeepCloner<T> build(Class<T> cls) {
        return new DeepCloner<T>().setClass(cls);
    }
}
