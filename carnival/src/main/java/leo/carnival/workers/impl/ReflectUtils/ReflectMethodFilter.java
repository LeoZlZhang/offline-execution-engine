package leo.carnival.workers.impl.ReflectUtils;

import leo.carnival.workers.prototype.Evaluator;
import leo.carnival.workers.prototype.Processor;

import java.lang.reflect.Method;

/**
 * Created by leo_zlzhang on 8/26/2016.
 * return method in target class which is hit Evaluator
 */
@SuppressWarnings("unused")
public final class ReflectMethodFilter implements Processor<Object, Method> {

    private Evaluator<Method> evaluator;


    @Override
    public Method process(Object targetObj) {
        if(targetObj == null)
            return null;

        for (Method method : targetObj.getClass().getMethods()) {
            if(evaluator == null || evaluator.evaluate(method))
                return method;
        }
        return null;
    }

    @Override
    public Method execute(Object o) {
        return process(o);
    }

    public ReflectMethodFilter setEvaluator(Evaluator<Method> evaluator) {
        this.evaluator = evaluator;
        return this;
    }
}
