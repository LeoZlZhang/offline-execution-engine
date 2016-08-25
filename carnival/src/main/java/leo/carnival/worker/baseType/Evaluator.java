package leo.carnival.worker.baseType;

public interface Evaluator<T> extends Worker<T, Boolean> {
    boolean evaluate(T obj);
}
