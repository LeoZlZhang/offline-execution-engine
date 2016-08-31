package leo.carnival.workers.impl.GearicUtils;

import leo.carnival.MyArrayUtils;
import leo.carnival.workers.prototype.Processor;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by leozhang on 8/28/16.
 * Clone array
 */
@SuppressWarnings("unchecked")
public class ArrayClone<T> implements Processor<T[], T[]> {
    private int cloneTimes;

    @Override
    public T[] process(T[] ts) {
        if (ts == null)
            return null;

        T[] rtnArray = (T[]) Array.newInstance(ts[0].getClass(), ts.length * cloneTimes);
        for (int i = 0; i < cloneTimes; i++)
            rtnArray = MyArrayUtils.mergeArray(rtnArray, Arrays.copyOf(ts, ts.length));
        return rtnArray;
    }

    @Override
    public T[] execute(T[] ts) {
        return process(ts);
    }

    public ArrayClone<T> setCloneNum(int cloneNum) {
        if (cloneNum < 0)
            return this;

        this.cloneTimes = cloneNum;
        return this;
    }

    public static <T> ArrayClone<T> build(int cloneTimes) {
        return new ArrayClone<T>().setCloneNum(cloneTimes);
    }
}
