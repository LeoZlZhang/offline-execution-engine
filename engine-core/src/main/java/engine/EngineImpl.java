package engine;


import leo.carnival.workers.baseType.Executor;
import leo.carnival.workers.baseType.Processor;
import leo.carnival.workers.baseType.Setter.ProcessorSetter;
import leo.carnival.workers.baseType.Worker;
import leo.carnival.workers.impl.JsonUtils.ClassDecorator;
import testCase.TestCase;
import engineFoundation.Assert.TestFail;
import engineFoundation.Assert.TestPass;
import engineFoundation.Assert.TestResult;
import engineFoundation.Flow.Flow;
import engineFoundation.Gear.Gear;
import engineFoundation.Step.Step;
import leo.carnival.workers.impl.JsonUtils.GsonUtils;
import leo.carnival.MyArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class EngineImpl implements Executor<String, TestResult>, ProcessorSetter<ClassDecorator, Executor> {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(EngineImpl.class);

    @Override
    public void loadGear(File gearFile) {
        try {
            this.gear = GsonUtils.firstOneFromJson(gearFile, Gear.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TestResult execute(TestCase testcase) {
        Map<String, Object> executingMap = new HashMap<>(context);
        executingMap.put("testCase", testcase);
        return execute(executingMap, testcase.getTestingFlow());
    }

    @Override
    public TestResult execute(String flowName) {
        return execute(context, flowName);
    }

    @Override
    public TestResult execute(Map<String, Object> context, String flowName, Object... obj) {
        try {
            Flow flow = MyArrayUtils.searchArray(gear.getFlows(), flowName);
            assert flow != null;

            Class sourceClass = Class.forName(flow.getSourceClass());
            Object FlowInstance = sourceClass.newInstance();

            Step[] steps = flow.getSteps();
            for (Step step : steps) {
                logger.info(String.format("[%d][%s][%s][%s]...", Thread.currentThread().getId(), gear.toString(), flowName, step.getMethod()));
                step.execute(context, FlowInstance);
            }
        } catch (Exception e) {
            return new TestFail(e);
        }
        return new TestPass();
    }

    @Override
    public <T> T getExecuteInfo(String key, Class<T> cla) {
        if (!context.containsKey(key))
            return null;
        return cla.cast(context.get(key));
    }

    @Override
    public String getExecuteInfo(String key) {
        return getExecuteInfo(key, String.class);
    }


    private Gear gear;

    @Override
    public TestResult execute(String flowName) {
        if (flowName == null || flowName.isEmpty())
            return new TestFail(String.format("Invalid flow name: %s", flowName));

        Flow flow = MyArrayUtils.searchArray(gear.getFlows(), flowName);
        if (flow == null)
            return new TestFail(String.format("Not found flow:%s", flowName));


        return null;
    }

    @Override
    public Worker setWorker(ClassDecorator worker) {
        return null;
    }
}
