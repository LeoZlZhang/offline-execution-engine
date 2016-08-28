package leo.carnival.workers.impl.CollectionUtils;

import leo.carnival.workers.baseType.Processor;

import java.util.Collection;

/**
 * Created by leozhang on 8/27/16.
 * Support type array
 */
public class FirstElementPicker<T> implements Processor<Collection<T>, T> {
    @Override
    public T process(Collection<T> ts) {

        return ts == null ? null : ts.iterator().hasNext() ? ts.iterator().next() : null;
    }

    @Override
    public T execute(Collection<T> ts) {
        return null;
    }

}
