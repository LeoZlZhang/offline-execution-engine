package engineFoundation.Step;


import leo.carnival.workers.filter.ReflectMethodFilter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class GeneralStep extends Step {

    @Override
    public void execute(Map<String, Object> context, Object flowInstance) throws InvocationTargetException, IllegalAccessException {
        //Step inputs
        Object[] inputParamArray = genParameterFromContext(context);

        //Step method
        Method method = new ReflectMethodFilter(flowInstance).process(getMethod(), inputParamArray.length);

        //Invoke step
        assert method != null;
        Object[] returnObj = (Object[]) method.invoke(flowInstance, inputParamArray);

        //Insert step result into context
        updateContext(context, returnObj);
    }

    @Override
    public int countInnerStepNum() {
        return super.countInnerStepNum();
    }
}
