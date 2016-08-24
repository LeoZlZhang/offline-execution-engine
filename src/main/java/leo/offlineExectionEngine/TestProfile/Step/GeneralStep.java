package leo.offlineExectionEngine.TestProfile.Step;


import com.vipabc.vliveshow.TestExecutionEngine.Engine.util.ReflectUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class GeneralStep extends Step {

    @Override
    public void execute(Map<String, Object> context, Object FlowInstance) throws InvocationTargetException, IllegalAccessException {
        //Step inputs
        Object[] inputParamArray = genParameterFromContext(context);

        //Step method
        Method method = ReflectUtil.findMethod(FlowInstance.getClass(), obj -> obj.getName().equals(getMethod()) && obj.getParameterCount() == inputParamArray.length);

        //Invoke step
        assert method != null;
        Object[] returnObj = (Object[]) method.invoke(FlowInstance, inputParamArray);

        //Insert step result into context
        updateContext(context, returnObj);
    }

    @Override
    public int countInnerStepNum() {
        return super.countInnerStepNum();
    }
}
