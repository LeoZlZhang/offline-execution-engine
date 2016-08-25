package leo.carnival.workers.baseType;

public interface Evaluator<T> extends Worker<T, Boolean> {
    boolean evaluate(T obj);
}
