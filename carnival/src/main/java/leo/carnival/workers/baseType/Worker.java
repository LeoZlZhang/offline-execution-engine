package leo.carnival.workers.baseType;

/**
 * Created by leo_zlzhang on 8/24/2016.
 * baseType interface for generic operation
 */
public interface Worker<T, G> {
    G execute(T t);
}
