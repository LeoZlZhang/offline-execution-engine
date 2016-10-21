package leo.engineCore.engineFoundation.Step;

import leo.carnival.workers.impl.Processors;
import leo.engineCore.engineFoundation.ApplicationContext;
import leo.engineData.testData.Bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@SuppressWarnings({"unused", "WeakerAccess", "FieldCanBeLocal"})
public class Step extends Bean<ApplicationContext, Step> {

    private String sourceClass;
    private String method;
    private String[] input;
    private String[] output;


    @Override
    public Step execute(ApplicationContext applicationContext) {
        if(method == null || method.isEmpty())
            throw new RuntimeException("Invalid method declared!");

        applicationContext.setStepName(method);
        applicationContext.setMethodRepo(Processors.ClassLoader().process(sourceClass));
        applicationContext.printExecutionInfo();


        //Step inputs
        Object[] parameters = getParameter(applicationContext.getContext());

        //Stepping
        Object[] returnObjs = invoke(applicationContext.getMethod(method), parameters);

        //Insert step result into context
        updateContext(applicationContext.getContext(), returnObjs);

        return this;
    }


    private Object[] getParameter(Map<String, Object> context) {
        if (input == null)
            return null;

        Object[] rtnArray = new Object[input.length];

        for (int i = 0, len = rtnArray.length; i < len; i++)
            rtnArray[i] = context.get(input[i]);

        return rtnArray;
    }




    private Object[] invoke(Map.Entry<Method, Object> entry, Object[] parameters) {
        try {
            return (Object[]) entry.getKey().invoke(entry.getValue(), parameters);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }


    private void updateContext(Map<String, Object> context, Object[] returnObjs) {
        if (output == null || output.length == 0)
            return;

        for (int i = 0, len = output.length; i < len; i++)
            context.put(output[i], returnObjs[i]);
    }


    @Override
    public String toString() {
        return method;
    }



    /**
     * Getter
     */
    public String getSourceClass() {
        return sourceClass;
    }
    public String getMethod() {
        return method;
    }
    public String[] getInput() {
        return input;
    }
    public String[] getOutput() {
        return output;
    }


    /**
     * Setter
     */

    public void setSourceClass(String sourceClass) {
        this.sourceClass = sourceClass;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public void setInput(String[] input) {
        this.input = input;
    }
    public void setOutput(String[] output) {
        this.output = output;
    }
}
