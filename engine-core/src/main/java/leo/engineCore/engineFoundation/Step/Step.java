package leo.engineCore.engineFoundation.Step;

import leo.engineCore.engineFoundation.ApplicationContext;
import leo.carnival.workers.prototype.Executor;

import static leo.carnival.workers.impl.GearicUtils.ClassUtils.loadClass;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Step implements Executor<ApplicationContext, Step>{
    public static final int NoLooping = -999;

    private String sourceClass;
    private int loop = NoLooping;
    private String method;
    private String[] input;
    private String[] output;

    @Override
    public String toString() {
        return method;
    }


    public int getLoop() {
        return loop;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String[] getInput() {
        return input;
    }

    public void setInput(String[] input) {
        this.input = input;
    }

    public String[] getOutput() {
        return output;
    }

    public void setOutput(String[] output) {
        this.output = output;
    }

    public int countInnerStepNum() {
        return 1;
    }

    @Override
    public Step execute(ApplicationContext applicationContext) {
        applicationContext.setStepName(method);
        applicationContext.printExecutionInfo();
        applicationContext.setMethodRepo(loadClass(sourceClass));
        return this;
    }
}
