package leo.carnival.workers.filter;

import leo.carnival.workers.baseType.Evaluator;

import java.lang.reflect.Method;

/**
 * Created by leo_zlzhang on 8/26/2016.
 * Evaluate a method which name and parameter length is matched
 */
public class ReflectMethodEvaluator implements Evaluator<Method> {

    private String methodName;
    private int parameterLength;

    public ReflectMethodEvaluator(String methodName, int parameterLength) {
        this.methodName = methodName;
        this.parameterLength = parameterLength;
    }

    @Override
    public boolean evaluate(Method obj) {
        return false;
    }

    @Override
    public Boolean execute(Method method) {
        return null;
    }
}
