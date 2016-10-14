package leo.engineCore.testEngine;


import leo.carnival.workers.impl.JacksonUtils;
import leo.carnival.workers.prototype.Executor;
import leo.engineCore.engineFoundation.Assert.TestFail;
import leo.engineCore.engineFoundation.Assert.TestResult;
import leo.engineCore.engineFoundation.Gear.Gear;
import leo.engineData.testData.TestData;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

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
            gear.getAppCtx().getContext().put("TestCase", testData);
            return gear.executeQuietly(testData.getWorkflow());
        } catch (Exception e) {
            return new TestFail(e);
        }
    }

    public TestEngine loadGearFromFile(File gearFile) throws IOException, JSONException {
        String jsonString = FileUtils.readFileToString(gearFile);
        ObjectMapper mapper = new ObjectMapper().disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        if (JacksonUtils.isJsonObject(jsonString))
            gear = JacksonUtils.fromJson(jsonString, Gear.class);
        else if (JacksonUtils.isJsonArray(jsonString))
            gear = JacksonUtils.firstOneFromJsonArray(jsonString, Gear.class);
        else
            throw new RuntimeException("Bad formatted json string:" + gearFile.getPath());

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
