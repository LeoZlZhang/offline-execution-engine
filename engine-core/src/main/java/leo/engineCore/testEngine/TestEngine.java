package leo.engineCore.testEngine;


import leo.carnival.workers.impl.JacksonUtils;
import leo.engineCore.engineFoundation.Assert.TestFail;
import leo.engineCore.engineFoundation.Assert.TestResult;
import leo.engineCore.engineFoundation.Gear.Gear;
import leo.engineData.testData.Bean;
import leo.engineData.testData.TestData;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;


@SuppressWarnings({"unused", "WeakerAccess"})
public class TestEngine extends Bean<String, TestResult> {
    private Gear gear;

    public TestEngine() {
    }

    public TestEngine(Gear gear) {
        this.gear = gear;
    }


    public static TestEngine build(Gear gear) {
        return new TestEngine(gear);
    }


    @Override
    public TestResult execute(String flowName) {
        try {
            return gear.setCustomLogger(logger).execute(flowName);
        } catch (Exception e) {
            return new TestFail(e);
        }
    }

    public TestResult execute(TestData testData) {
        try {
            gear.appCtx().getContext().put("TestCase", testData.setCustomLogger(logger));
            return gear.setCustomLogger(logger).execute(testData.getWorkflow());
        } catch (Exception e) {
            return new TestFail(e);
        }
    }

    public TestEngine loadGearFromFile(File gearFile) throws IOException, JSONException {
        String jsonString = FileUtils.readFileToString(gearFile);
        if (JacksonUtils.isJsonObject(jsonString))
            gear = JacksonUtils.fromJson(jsonString, Gear.class);
        else if (JacksonUtils.isJsonArray(jsonString))
            gear = JacksonUtils.firstOneFromJsonArray(jsonString, Gear.class);
        else
            throw new RuntimeException("Bad formatted json string:" + gearFile.getPath());

        return this;
    }

}
