package com.vipabc.vliveshow.apitest.bean;

import com.vipabc.vliveshow.apitest.bean.asset.TestAsset;
import leo.engineData.testData.TestDataImpl;

import java.io.Serializable;


@SuppressWarnings({"unused", "WeakerAccess"})
public class APITestData extends TestDataImpl implements Serializable{

    private String name;

    private String workflow;

    private TestAsset[] assets;

    private String sourceFileName = "";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkflow() {
        return workflow;
    }

    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }

    public TestAsset[] getAssets() {
        return assets;
    }

    public void setAssets(TestAsset[] assets) {
        this.assets = assets;
    }

    @Override
    public String getTestingFlow() {
        return getWorkflow();
    }

    @Override
    public String getSourceFileName() {
        return sourceFileName;
    }

    @Override
    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
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
