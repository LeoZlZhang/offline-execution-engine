package worker;

import leo.carnival.workers.prototype.Processor;

/**
 * Created by leo_zlzhang on 8/29/2016.
 * Pick in cycle orderly
 */
@SuppressWarnings("WeakerAccess")
public class CycleNumPicker implements Processor<Integer, Integer> {
    private int seed = 0;

    @Override
    public Integer process(Integer range) {
        seed = seed == Integer.MAX_VALUE ? 0 : seed++;
        return seed % range;
    }

    @Override
    public Integer execute(Integer integer) {
        return process(integer);
    }


}
