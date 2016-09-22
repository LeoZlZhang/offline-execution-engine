package leo.carnival.workers.impl;

import leo.carnival.workers.impl.GearicUtils.ScriptExecutor;

/**
 * Created by leo_zlzhang on 9/22/2016.
 * Container of executor
 */
public class Executors {

    public static ScriptExecutor scriptExecutor(){
        return new ScriptExecutor();
    }


    private static final Executors executors = new Executors();

    private Executors(){}

    public static Executors build(){
        return executors;
    }
}
