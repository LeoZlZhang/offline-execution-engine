package com.vipabc.vliveshow.apitest.TestFlow;

import com.google.api.client.http.HttpResponse;
import com.vipabc.vliveshow.apitest.bean.APITestData;
import com.vipabc.vliveshow.apitest.bean.asset.ResponseContainer;
import com.vipabc.vliveshow.apitest.bean.asset.TestAsset;
import leo.carnival.MyArrayUtils;
import leo.carnival.workers.impl.JsonUtils.InstanceUpdater;
import leo.carnival.workers.impl.JsonUtils.MapValueUpdater;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class APITest {
    private static final Logger logger = Logger.getLogger(APITest.class);


    public void prepare() {
        logger.info("Nothing to prepare");
    }

    public void end() {
        logger.info("End");
    }

    public Object[] loadTestData(APITestData tc) {
        logger.info(tc.toString());
        return new Object[]{tc};
    }

    public Object[] initializeLoop(APITestData tc) {
        Assert.assertTrue(tc.getAssets().length > 0);
        return new Object[]{tc.getAssets()[0], new HashMap<String, Object>()};
    }

    public Object[] loadNextTestAsset(APITestData tc, TestAsset asset) {
        int index = MyArrayUtils.getElementIndexInArray(tc.getAssets(), asset);
        Assert.assertTrue(index >= 0);

        if ((index + 1) == tc.getAssets().length)
            return new Object[]{asset, true};
        else
            return new Object[]{tc.getAssets()[index + 1], false};
    }


    public Object[] requestWithHttpClient(TestAsset ori_asset, Map<String, Object> extractionKVMap) throws Exception {
        TestAsset asset = (TestAsset) InstanceUpdater.build(MapValueUpdater.build(extractionKVMap)).process(ori_asset);

        HttpResponse response = asset.getRequest().process();

        ResponseContainer responseContainer = new ResponseContainer(response);

        asset.evaluate(responseContainer);

        asset.extract(responseContainer, extractionKVMap);

        return new Object[]{extractionKVMap};
    }
}
