package leo.carnival.workers.baseType;

public interface Generator<T, G> extends Worker<T, G> {
    G generate(T t);
}
