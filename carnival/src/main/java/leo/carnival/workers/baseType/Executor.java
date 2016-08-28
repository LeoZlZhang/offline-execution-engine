package leo.carnival.workers.baseType;

/**
 * Created by leozhang on 8/28/16.
 * Executor
 */
public interface Executor<T, G> extends Worker<T, G>{

    G execute(T t);
}
