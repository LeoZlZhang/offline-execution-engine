package engineFoundation.Step;


import engineFoundation.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class GeneralStep extends Step {


    @Override
    public GeneralStep execute(ApplicationContext applicationContext) {
        super.execute(applicationContext);

        //Step inputs
        Object[] parameters = getParameter(applicationContext.getContext());

        //Stepping
        Object[] returnObjs = invoke(applicationContext.getMethod(getMethod()), parameters);

        //Insert step result into context
        updateContext(applicationContext.getContext(), returnObjs);

        return this;
    }

    @Override
    public int countInnerStepNum() {
        return super.countInnerStepNum();
    }


    private Object[] getParameter(Map<String, Object> context) {
        if (getInput() == null)
            return null;

        Object[] rtnArray = new Object[getInput().length];

        for (int i = 0, len = rtnArray.length; i < len; i++)
            rtnArray[i] = context.get(getInput()[i]);

        return rtnArray;
    }


    private void updateContext(Map<String, Object> context, Object[] returnObjs) {
        if (getOutput() == null && getOutput().length == 0)
            return;

        for (int i = 0, len = getOutput().length; i < len; i++)
            context.put(getOutput()[i], returnObjs[i]);
    }

    private Object[] invoke(Map.Entry<Method, Object> entry, Object[] parameters) {
        try {
            return (Object[]) entry.getKey().invoke(entry.getValue(), parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to invode method:" + entry.getKey());
        }
    }
}
