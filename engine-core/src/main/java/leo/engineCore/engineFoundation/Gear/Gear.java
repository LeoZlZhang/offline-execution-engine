package leo.engineCore.engineFoundation.Gear;


import leo.carnival.MyArrayUtils;
import leo.carnival.workers.impl.Processors;
import leo.engineCore.engineFoundation.ApplicationContext;
import leo.engineCore.engineFoundation.Assert.TestFail;
import leo.engineCore.engineFoundation.Assert.TestPass;
import leo.engineCore.engineFoundation.Assert.TestResult;
import leo.engineCore.engineFoundation.Flow.Flow;
import leo.engineData.testData.Bean;
import org.apache.log4j.Logger;


@SuppressWarnings({"ConstantConditions", "unused"})
public class Gear extends Bean<String, TestResult> {
    private String sourceClass;
    private String name;
    private Flow[] flows;
    private ApplicationContext applicationContext = new ApplicationContext();


    @Override
    public TestResult execute(String flowName) {
        try {
            applicationContext.setGearName(name);
            applicationContext.setMethodRepo(Processors.ClassLoader().process(sourceClass));
            Flow flow = MyArrayUtils.searchArray(flows, flowName);
            flow.setCustomLogger(logger).execute(applicationContext);
            return new TestPass();
        } catch (Exception e) {
            return new TestFail(e);
        }
    }

    @Override
    public String toString() {
        return name;
    }


    public ApplicationContext appCtx() {
        return applicationContext;
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

    public Flow[] getFlows() {
        return flows;
    }

    /**
     * setter
     */
    public void setSourceClass(String sourceClass) {
        this.sourceClass = sourceClass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFlows(Flow[] flows) {
        this.flows = flows;
    }

    @Override
    public Bean<String, TestResult> setCustomLogger(Logger logger) {
        applicationContext.setLogger(logger);
        return super.setCustomLogger(logger);
    }
}
