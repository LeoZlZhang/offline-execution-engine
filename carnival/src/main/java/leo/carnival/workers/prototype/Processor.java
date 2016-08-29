package leo.carnival.workers.prototype;


public interface Processor<T, G> extends Worker<T, G> {
    G process(T t);
}
