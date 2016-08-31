package leo.carnival.workers.implementation.GearicUtils;

import leo.carnival.workers.prototype.Processor;

import java.io.*;


/**
 * Created by leo_zlzhang on 8/29/2016.
 * Deep clone
 */
public class DeepClone<T> implements Processor<T, T> {

    @SuppressWarnings("unchecked")
    @Override
    public T process(T t) {
        try {
            //将对象写到流里
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(t);
            //从流里读出来
            ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi = new ObjectInputStream(bi);
            return (T) oi.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to clone class: " + t.getClass(), e);
        }
    }


    @Override
    public T execute(T t) {
        return process(t);
    }


}
