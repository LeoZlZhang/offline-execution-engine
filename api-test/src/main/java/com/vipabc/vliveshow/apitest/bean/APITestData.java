package com.vipabc.vliveshow.apitest.bean;

import com.vipabc.vliveshow.apitest.bean.asset.TestAsset;
import leo.engineData.testData.TestDataImpl;

import java.io.Serializable;


@SuppressWarnings("unused")
public class APITestData extends TestDataImpl implements Serializable{

    private String name;

    private String workflow;

    private TestAsset[] assets;


    private String sourceFileName = "";

    public String getName() {
        return name;
    }

    public String getWorkflow() {
        return workflow;
    }

    public TestAsset[] getAssets() {
        return assets;
    }

    public APITestData getInstance() {
        return new APITestData();
    }

    public String getTestingFlow() {
        return this.workflow;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String name) {
        this.sourceFileName = name;
    }


    @Override
    public String toString() {
        return String.format("'%s'--->'%s'", sourceFileName, name);
    }

    @Override
    public Object process(Object o) {
        return null;
    }

    @Override
    public Object execute(Object o) {
        return null;
    }
}
