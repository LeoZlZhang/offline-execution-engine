package engineFoundation.Step;

import engineFoundation.ApplicationContext;
import leo.carnival.workers.baseType.Executor;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Map;

@SuppressWarnings("unused")
public class Step implements Executor<ApplicationContext, Boolean>{
    protected static final Logger logger = Logger.getLogger(Step.class);
    public static final int NoLooping = -999;

    private int loop = NoLooping;
    private String method;
    private String[] input = new String[0];
    private String[] output = new String[0];

    @Override
    public String toString() {
        return method;
    }


    public String getMethod() {
        return method;
    }

    public int getLoop() {
        return loop;
    }

    public String[] getOutput() {
        return output;
    }

    public String[] getInput() {
        return input;
    }


    public void setMethod(String method) {
        this.method = method;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }

    public void setInput(String[] input) {
        this.input = input;
    }

    public void setOutput(String[] output) {
        this.output = output;
    }

    public void execute(Map<String, Object> context, Object FlowInstance) throws Exception {
    }

    public int countInnerStepNum() {
        return 1;
    }

    protected Object[] genParameterFromContext(Map<String, Object> context) {
        Object[] rtnArray = new Object[input.length];
        for (int i = 0, len = input.length; i < len; i++)
            rtnArray[i] = context.get(input[i]);
        return rtnArray;
    }

    protected void updateContext(Map<String, Object> context, Object[] stepResult) {
        if (this.output.length == 0)
            return;
        assert this.output.length == stepResult.length;
        for (int i = 0, len = this.output.length; i < len; i++)
            context.put(this.output[i], stepResult[i]);
    }

    public <T extends Step> T transferToChild(Class<T> cls) throws IllegalAccessException, InstantiationException {
        T rtnStep = cls.newInstance();
        rtnStep.setMethod(this.getMethod());
        rtnStep.setLoop(this.getLoop());
        rtnStep.setInput(Arrays.copyOf(this.getInput(),this.getInput().length));
        rtnStep.setOutput(Arrays.copyOf(this.getOutput(),this.getOutput().length));
        return rtnStep;
    }

    @Override
    public Boolean execute(ApplicationContext applicationContext) {
        todo
        return null;
    }
}
