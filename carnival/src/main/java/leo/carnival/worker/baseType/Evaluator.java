package leo.carnival.worker.baseType;

public interface Evaluator<T> extends Executable<T, Boolean> {
    boolean evaluate(T obj);
}
