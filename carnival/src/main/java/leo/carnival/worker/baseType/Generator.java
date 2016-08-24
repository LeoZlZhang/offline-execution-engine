package leo.carnival.worker.baseType;

public interface Generator<T, G> extends Executable<T, G> {
    G generate(T t);
}
