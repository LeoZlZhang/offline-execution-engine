package leo.offlineExectionEngine.Util.Worker;

public interface Evaluator<T> {
    boolean evaluate(T obj) throws Exception;
}
