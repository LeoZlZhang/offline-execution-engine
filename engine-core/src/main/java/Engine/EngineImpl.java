package Engine;


import TestCase.TestCase;
import engineFoundation.Assert.TestFail;
import engineFoundation.Assert.TestPass;
import engineFoundation.Assert.TestResult;
import engineFoundation.Flow.Flow;
import engineFoundation.Gear.Gear;
import engineFoundation.Step.Step;
import leo.carnival.GsonUtils;
import leo.carnival.MyArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class EngineImpl extends AbstractEngine {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(EngineImpl.class);

    @Override
    public void loadGear(File gearFile) {
        try {
            this.gear= GsonUtils.firstOneFromJson(gearFile, Gear.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TestResult execute(TestCase testcase) {
        Map<String, Object> executingMap = new HashMap<>(context);
        executingMap.put("TestCase", testcase);
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
            Object FlowInstance = sourceClass.getConstructors()[0].newInstance();

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



}
