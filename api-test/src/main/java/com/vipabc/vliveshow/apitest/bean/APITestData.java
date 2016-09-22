package com.vipabc.vliveshow.apitest.bean;

import com.vipabc.vliveshow.apitest.bean.asset.TestAsset;
import leo.engineData.testData.TestDataImpl;

import java.io.Serializable;


@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class APITestData extends TestDataImpl implements Serializable {
    private String name;
    private String workflow;
    private TestAsset[] assets;


    private String sourceFileName = "";


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

    @Override
    public String getTestingFlow() {
        return this.workflow;
    }

    /**
     * Getter
     */
    public String getName() {
        return name;
    }

    public String getWorkflow() {
        return workflow;
    }

    public TestAsset[] getAssets() {
        return assets;
    }

    /**
     * Setter
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }

    public void setAssets(TestAsset[] assets) {
        this.assets = assets;
    }

    @Override
    public String getSourceFileName() {
        return sourceFileName;
    }

    @Override
    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }
}
