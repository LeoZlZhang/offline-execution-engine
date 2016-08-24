package leo.carnival.worker.baseType;


public interface Processor<T, G> extends Executable<T, G> {
    G process(T t);
}
