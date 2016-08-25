package leo.carnival.worker.baseType;


public interface Processor<T, G> extends Worker<T, G> {
    G process(T t);
}
