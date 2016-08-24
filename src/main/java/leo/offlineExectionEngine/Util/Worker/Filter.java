package leo.offlineExectionEngine.Util.Worker;

public interface Filter<T> {

    boolean evaluate(T obj);

}

