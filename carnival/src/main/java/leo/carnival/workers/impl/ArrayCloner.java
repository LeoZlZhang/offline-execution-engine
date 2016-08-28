package leo.carnival.workers.impl;

import leo.carnival.MyArrayUtils;
import leo.carnival.workers.baseType.Processor;

import java.lang.reflect.Array;

/**
 * Created by leozhang on 8/28/16.
 * Clone array
 */
@SuppressWarnings("unchecked")
public class ArrayCloner<T> implements Processor<T[], T[]> {
    private int cloneTimes;

    @Override
    public T[] process(T[] ts) {
        T[] rtnArray = (T[]) Array.newInstance(ts[0].getClass(), 0);
        for (int i = 0; i < cloneTimes; i++)
           rtnArray = MyArrayUtils.appendArray(rtnArray, ts);

        return rtnArray;
    }

    @Override
    public T[] execute(T[] ts) {
        return process(ts);
    }

    public ArrayCloner<T> setCloneNum(int cloneNum) {
        if (cloneNum < 0)
            return this;

        this.cloneTimes = cloneNum;
        return this;
    }

    public static<T> ArrayCloner<T> build(int cloneTimes) {
        return new ArrayCloner<T>().setCloneNum(cloneTimes);
    }
}
