package leo.engineCore.engineFoundation.Step;

import leo.carnival.workers.impl.Processors;
import leo.engineCore.engineFoundation.ApplicationContext;
import leo.carnival.workers.prototype.Executor;

@SuppressWarnings({"unused", "WeakerAccess", "FieldCanBeLocal"})
public class Step implements Executor<ApplicationContext, Step>{
    public static final int NoLooping = -999;

    private String sourceClass;
    private int loop = NoLooping;
    private String method;
    private String[] input;
    private String[] output;



    public static int getNoLooping() {
        return NoLooping;
    }

    @Override
    public String toString() {
        return method;
    }

    public int countInnerStepNum() {
        return 1;
    }

    @Override
    public Step execute(ApplicationContext applicationContext) {
        applicationContext.setStepName(method);
        applicationContext.printExecutionInfo();
        applicationContext.setMethodRepo(Processors.ClassLoader().process(sourceClass));
        return this;
    }

    /**
     * Getter
     */
    public String getSourceClass() {
        return sourceClass;
    }

    public int getLoop() {
        return loop;
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

    public void setLoop(int loop) {
        this.loop = loop;
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
