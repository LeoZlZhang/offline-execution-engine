package leo.carnival.workers.baseType;


public interface Processor<T, G> extends Worker<T, G> {
    G process(T t);
}
