package leo.carnival.workers.impl;


/**
 * Created by leo_zlzhang on 9/22/2016.
 *
 */
public class Workers {
    public static Evaluators Evaluators(){
        return Evaluators.build();
    }

    public static Executors Executors(){
        return Executors.build();
    }

    public static Processors Processors(){
        return Processors.build();
    }
}
