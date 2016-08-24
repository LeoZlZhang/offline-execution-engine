package leo.offlineExectionEngine.Util.Worker;


public interface Processor<T,G> {
    G process(T t);
}
