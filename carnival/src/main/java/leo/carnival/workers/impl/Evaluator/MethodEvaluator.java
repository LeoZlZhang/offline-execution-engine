package leo.carnival.workers.impl.Evaluator;

import leo.carnival.workers.prototype.Evaluator;

import java.lang.reflect.Method;

/**
 * Created by leo_zlzhang on 9/22/2016.
 * Method search
 */
public class MethodEvaluator implements Evaluator<Method> {
    private String expectedMethodName;
    private Integer expectedParamLength;

    @Override
    public boolean evaluate(Method method) {
        return method != null &&
                (expectedMethodName == null || method.getName().equalsIgnoreCase(expectedMethodName)) &&
                (expectedParamLength == null || method.getParameterCount() == expectedParamLength);
    }

    @Override
    public Boolean execute(Method method) {
        return evaluate(method);
    }

    public MethodEvaluator setExpectedMethodName(String name) {
        this.expectedMethodName = name;
        return this;
    }

    public MethodEvaluator setExpectedParamLength(Integer length) {
        this.expectedParamLength = length;
        return this;
    }
}
