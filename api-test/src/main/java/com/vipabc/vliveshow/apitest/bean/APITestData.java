package com.vipabc.vliveshow.apitest.bean;

import com.vipabc.vliveshow.apitest.bean.asset.TestAsset;
import leo.engineData.testData.TestData;

import java.io.Serializable;
import java.util.Map;


public class APITestData extends TestData<Map<String, Object>, Map<String, Object>> implements Serializable {

    private String name;
    private TestAsset[] assets;



    @Override
    public Map<String, Object> execute(Map<String, Object> extractionMap) {

        for(TestAsset testAsset : assets)
            testAsset.setCustomLogger(logger).execute(extractionMap);

        return extractionMap;
    }


    @Override
    public String toString() {
        return String.format("'%s'--->'%s'", getSourceFileName(), name);
    }





    /**
     * Getter
     */
    public String getName() {
        return name;
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


    public void setAssets(TestAsset[] assets) {
        this.assets = assets;
    }

}
