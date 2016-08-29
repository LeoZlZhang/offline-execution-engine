package leo.carnival.workers.prototype;

/**
 * Created by leo_zlzhang on 8/24/2016.
 * prototype interface for generic operation
 */
public interface Worker<T, G> {
    G execute(T t);
}
