package com.vipabc.vliveshow.apitest;

import com.vipabc.vliveshow.apitest.bean.APITestData;
import leo.engineData.testData.TestData;
import leo.testngBase.AbstractMainTest;
import leo.testngBase.TestInfo;
import org.testng.annotations.Test;

import java.util.Map;


@SuppressWarnings({"SynchronizationOnLocalVariableOrMethodParameter", "DefaultAnnotationParam"})
public class APIMainTest extends AbstractMainTest {

    @TestInfo(testDataClass = APITestData.class,
            threadNumber = 1,
            repeatTime = 1,
            profileFolderName = "MyEnvProfile",
            dataFlowFolderName = "DataFlow",
            dataFolderName = "Data",
            testDataFilterRegex = ".*\\.json")
    @Test(dataProvider = "DataSource")
    public void apiTest(TestData testCase) {
        Map<String, Object> profile = profilePicker.next();
        synchronized (profile) {
            engine.execute(testCase.update(profile)).Assert();
        }
    }


}
