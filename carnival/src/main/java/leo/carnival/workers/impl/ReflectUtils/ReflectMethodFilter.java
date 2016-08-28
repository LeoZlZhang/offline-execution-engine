package leo.carnival.workers.impl.ReflectUtils;

import leo.carnival.workers.baseType.Evaluator;
import leo.carnival.workers.baseType.Processor;
import leo.carnival.workers.baseType.Setter.EvaluatorSetter;

import java.lang.reflect.Method;

/**
 * Created by leo_zlzhang on 8/26/2016.
 * return method in target class which is hit Evaluator
 */
@SuppressWarnings("unused")
public final class ReflectMethodFilter implements Processor<Object, Method>, EvaluatorSetter<Evaluator<Method>> {

    private Evaluator<Method> methodEvaluator;


    @Override
    public Method process(Object targetObj) {
        if(targetObj == null)
            return null;

        for (Method method : targetObj.getClass().getMethods()) {
            if(methodEvaluator == null || methodEvaluator.evaluate(method))
                return method;
        }
        return null;
    }

    @Override
    public Method execute(Object o) {
        return process(o);
    }

    @Override
    public ReflectMethodFilter setWorker(Evaluator<Method> worker) {
        this.methodEvaluator = worker;
        return this;
    }

    public static ReflectMethodFilter build() throws IllegalAccessException, InstantiationException {
        return new ReflectMethodFilter();
    }

    public static ReflectMethodFilter build(Evaluator<Method> methodEvaluator) throws InstantiationException, IllegalAccessException {
        return ReflectMethodFilter.build().setWorker(methodEvaluator);
    }

    public static ReflectMethodFilter build(String methodName, int paramLength) throws InstantiationException, IllegalAccessException {
        return ReflectMethodFilter.build().setWorker(new Evaluator<Method>() {
            @Override
            public boolean evaluate(Method method) {
                return method != null && (method.getName().equalsIgnoreCase(methodName) && method.getParameterCount() == paramLength);
            }

            @Override
            public Boolean execute(Method method) {
                return evaluate(method);
            }
        });
    }
}
