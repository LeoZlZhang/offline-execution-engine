package leo.carnival.workers.filter;

import leo.carnival.workers.baseType.Evaluator;
import leo.carnival.workers.baseType.Processor;

import java.lang.reflect.Method;

/**
 * Created by leo_zlzhang on 8/26/2016.
 * return method in T which is hit Evaluator
 */
public class ReflectMethodFilter implements Processor<Evaluator<Method>, Method> {

    private Object targetObj;

    public ReflectMethodFilter(Object targetObj) {
        this.targetObj = targetObj;
    }

    @Override
    public Method process(Evaluator<Method> methodEvaluator) {
        if(targetObj == null || methodEvaluator == null)
            return null;

        for (Method method : targetObj.getClass().getMethods()) {
            if(methodEvaluator.evaluate(method))
                return method;
        }
        return null;
    }

    public Method process(String methodName, int paramLength){
        return process(new Evaluator<Method>() {
            @Override
            public boolean evaluate(Method method) {
                return method!= null && (method.getName().equalsIgnoreCase(methodName) && method.getParameterCount() == paramLength);
            }

            @Override
            public Boolean execute(Method method) {
                return null;
            }
        });
    }



    @Override
    public Method execute(Evaluator<Method> methodEvaluator) {
        return process(methodEvaluator);
    }
}
