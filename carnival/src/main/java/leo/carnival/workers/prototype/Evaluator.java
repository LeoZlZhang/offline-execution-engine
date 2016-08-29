package leo.carnival.workers.prototype;

public interface Evaluator<T> extends Worker<T, Boolean> {
    boolean evaluate(T obj);
}
