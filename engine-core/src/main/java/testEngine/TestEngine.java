package testEngine;


import engineFoundation.Assert.TestFail;
import engineFoundation.Assert.TestPass;
import engineFoundation.Assert.TestResult;
import engineFoundation.Gear.Gear;
import leo.carnival.workers.implementation.JsonUtils.GsonUtils;
import leo.carnival.workers.prototype.Executor;
import org.apache.commons.io.FileUtils;
import testData.TestData;

import java.io.File;
import java.io.IOException;


@SuppressWarnings("unused")
public class TestEngine implements Executor<String, TestResult> {
    private Gear gear;

    @Override
    public TestResult execute(String flowName) {
        try {
            gear.execute(flowName);
        } catch (Exception e) {
            return new TestFail(e);
        }
        return new TestPass();
    }

    public TestResult execute(TestData testData) {
        try {
            gear.getApplicationContext().getContext().put("testData", testData);
            gear.executeQuietly(testData.getTestingFlow());
        } catch (Exception e) {
            return new TestFail(e);
        }
        return new TestPass();
    }

    public TestEngine loadGearFromFile(File gearFile) throws IOException {
        String jsonString = FileUtils.readFileToString(gearFile);
        if (GsonUtils.isJsonObject(jsonString))
            gear = GsonUtils.fromJsonObject(jsonString, Gear.class);
        if (GsonUtils.isJsonArray(jsonString))
            gear = GsonUtils.firstOneFromJsonArray(jsonString, Gear.class);
        return this;
    }

    public static TestEngine build(File gearFile) throws IOException {
        TestEngine engine = new TestEngine();
        String jsonString = FileUtils.readFileToString(gearFile);
        if (GsonUtils.isJsonObject(jsonString))
            engine.gear = GsonUtils.fromJsonObject(jsonString, Gear.class);
        if (GsonUtils.isJsonArray(jsonString))
            engine.gear = GsonUtils.firstOneFromJsonArray(jsonString, Gear.class);
        return engine;
    }
}
