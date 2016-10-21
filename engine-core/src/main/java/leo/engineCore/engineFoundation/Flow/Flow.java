package leo.engineCore.engineFoundation.Flow;


import leo.carnival.workers.impl.Processors;
import leo.engineCore.engineFoundation.ApplicationContext;
import leo.engineCore.engineFoundation.Step.Step;
import leo.engineData.testData.Bean;


@SuppressWarnings("unused")
public class Flow extends Bean<ApplicationContext, Flow> {
    private String sourceClass;
    private String name;
    private Step[] steps;

    @Override
    public Flow execute(ApplicationContext applicationContext) {
        applicationContext.setFlowName(name);
        applicationContext.setMethodRepo(Processors.ClassLoader().process(sourceClass));
        for (Step step : steps)
            step.setCustomLogger(logger).execute(applicationContext);
        return this;
    }

    @Override
    public String toString() {
        return name;
    }


    /**
     * Getter
     */
    public String getSourceClass() {
        return sourceClass;
    }

    public String getName() {
        return name;
    }

    public Step[] getSteps() {
        return steps;
    }

    /**
     * Setter
     */
    public void setSourceClass(String sourceClass) {
        this.sourceClass = sourceClass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }
}
