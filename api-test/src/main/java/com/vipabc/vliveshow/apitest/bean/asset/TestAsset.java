package com.vipabc.vliveshow.apitest.bean.asset;


import com.vipabc.vliveshow.apitest.bean.asset.assertions.Assertion;
import com.vipabc.vliveshow.apitest.bean.asset.dbOperation.DBOperation;
import com.vipabc.vliveshow.apitest.bean.asset.extraction.Extraction;
import com.vipabc.vliveshow.apitest.bean.asset.request.Request;
import com.vipabc.vliveshow.apitest.bean.asset.setting.Setting;
import leo.carnival.workers.impl.Executors;
import leo.carnival.workers.impl.JsonUtils.InstanceUpdater;
import leo.carnival.workers.impl.JsonUtils.MapValueUpdater;
import leo.carnival.workers.prototype.Executor;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings({"unused", "WeakerAccess"})
public class TestAsset implements Serializable, Executor<Map<String, Object>, Map<String, Object>> {
    private static final Logger logger = Logger.getLogger(TestAsset.class);

    private String info;
    private Request request;
    private DBOperation dbOperation;
    private Assertion assertions;
    private Extraction extractions;
    private Setting settings;

    private Integer repeat;
    private TestAsset[] assets;


    @Override
    public Map<String, Object> execute(Map<String, Object> extractionKVMap) {

        if (repeat != null && repeat > 0 && assets != null && assets.length > 0)
            executeAssets(extractionKVMap);
        else
            executeLocal(extractionKVMap);

        return extractionKVMap;
    }


    public void executeLocal(Map<String, Object> extractionKVMap) {

        TestAsset asset = (TestAsset) InstanceUpdater.build(MapValueUpdater.build(extractionKVMap).setScriptEngine(Executors.scriptExecutor())).process(this);

        if (asset.getInfo() != null)
            logger.info(asset.getInfo());

        try {

            ResponseContainer responseContainer = null;

            if (asset.getRequest() != null)
                responseContainer = new ResponseContainer(asset.getRequest().process());

            else if (asset.getDbOperation() != null)
                responseContainer = new ResponseContainer(asset.getDbOperation().process(extractionKVMap));

            if (asset.getAssertions() != null)
                asset.getAssertions().evaluate(responseContainer);

            if (asset.getExtractions() != null)
                asset.getExtractions().setExtractionMap(extractionKVMap).process(responseContainer);

            if (asset.getSettings() != null)
                asset.getSettings().process(extractionKVMap);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void executeAssets(Map<String, Object> extractionKVMap) {
        for (int i = 0; i < repeat; i++)
            for (TestAsset testAsset : assets)
                testAsset.execute(extractionKVMap);
    }


    @Override
    public String toString() {
        return request == null ? "" : request.toString();
    }


    /**
     * Getter
     */
    public String getInfo() {
        return info;
    }

    public Integer getRepeat() {
        return repeat;
    }

    public Request getRequest() {
        return request;
    }

    public DBOperation getDbOperation() {
        return dbOperation;
    }

    public TestAsset[] getAssets() {
        return assets;
    }

    public Assertion getAssertions() {
        return assertions;
    }

    public Extraction getExtractions() {
        return extractions;
    }

    public Setting getSettings() {
        return settings;
    }

    /**
     * Setter
     */
    public void setInfo(String info) {
        this.info = info;
    }

    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void setDbOperation(DBOperation dbOperation) {
        this.dbOperation = dbOperation;
    }

    public void setAssets(TestAsset[] assets) {
        this.assets = assets;
    }

    public void setAssertions(Assertion assertions) {
        this.assertions = assertions;
    }

    public void setExtractions(Extraction extractions) {
        this.extractions = extractions;
    }

    public void setSettings(Setting settings) {
        this.settings = settings;
    }
}
