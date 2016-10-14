package com.vipabc.vliveshow.apitest.bean;

import com.vipabc.vliveshow.apitest.bean.asset.TestAsset;
import leo.engineData.testData.TestDataImpl;

import java.io.Serializable;
import java.util.Map;


@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class APITestData extends TestDataImpl<Map<String, Object>, Map<String, Object>> implements Serializable {
    private String name;
    private String workflow;
    private TestAsset[] assets;


    private String sourceFileName;

    @Override
    public Map<String, Object> execute(Map<String, Object> extractionMap) {

        for(TestAsset testAsset : assets)
            testAsset.execute(extractionMap);

        return extractionMap;
    }


    @Override
    public String toString() {
        return String.format("'%s'--->'%s'", sourceFileName, name);
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
