package worker;

import leo.carnival.workers.prototype.Processor;

import java.util.Random;

/**
 * Created by leo_zlzhang on 8/29/2016.
 * pick number randomly
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class RandomNumPicker implements Processor<Integer, Integer>{
    private Random random = new Random();
    @Override
    public Integer process(Integer range) {
        return random.nextInt(range);
    }

    @Override
    public Integer execute(Integer integer) {
        return process(integer);
    }

    public static RandomNumPicker build(){
        return new RandomNumPicker();
    }
}
