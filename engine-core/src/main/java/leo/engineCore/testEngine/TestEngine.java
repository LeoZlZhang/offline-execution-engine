package leo.engineCore.testEngine;


import leo.carnival.workers.impl.GsonUtils;
import leo.carnival.workers.prototype.Executor;
import leo.engineCore.engineFoundation.Assert.TestFail;
import leo.engineCore.engineFoundation.Assert.TestResult;
import leo.engineCore.engineFoundation.Gear.Gear;
import leo.engineData.testData.TestData;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;


@SuppressWarnings("unused")
public class TestEngine implements Executor<String, TestResult> {
    private Gear gear;

    @Override
    public TestResult execute(String flowName) {
        try {
            return gear.execute(flowName);
        } catch (Exception e) {
            return new TestFail(e);
        }
    }

    public TestResult execute(TestData testData) {
        try {
            gear.getApplicationContext().getContext().put("TestCase", testData);
            return gear.executeQuietly(testData.getTestingFlow());
        } catch (Exception e) {
            return new TestFail(e);
        }
    }

    public TestEngine loadGearFromFile(File gearFile) throws IOException, JSONException {
        String jsonString = FileUtils.readFileToString(gearFile);
        if (GsonUtils.isJsonObject(jsonString))
            gear = GsonUtils.fromJsonObject(jsonString, Gear.class);
        else if (GsonUtils.isJsonArray(jsonString))
            gear = GsonUtils.firstOneFromJsonArray(jsonString, Gear.class);
        else {
            new JSONObject(jsonString);
            new JSONArray(jsonString);
            throw new RuntimeException("Bad formatted json string:" + gearFile.getPath());
        }
        return this;
    }

    public static TestEngine build(File gearFile) {
        try {
            return new TestEngine().loadGearFromFile(gearFile);
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
