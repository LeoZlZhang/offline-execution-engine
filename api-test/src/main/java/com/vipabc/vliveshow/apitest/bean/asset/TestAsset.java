package com.vipabc.vliveshow.apitest.bean.asset;


import com.vipabc.vliveshow.apitest.bean.asset.assertions.AssertType;
import com.vipabc.vliveshow.apitest.bean.asset.request.Request;
import com.vipabc.vliveshow.apitest.Util.JsonExtractor;
import leo.carnival.workers.impl.JsonUtils.GsonUtils;
import leo.carnival.workers.prototype.Evaluator;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings({"unused", "DefaultAnnotationParam", "MismatchedQueryAndUpdateOfCollection", "unchecked"})
public class TestAsset implements Evaluator<ResponseContainer>, Serializable{
    private static final Logger logger = Logger.getLogger(TestAsset.class);

    private String info;

    private Request request;

    private Map<AssertType, Object> assertions;

    private Map<String, Object> extractions;

    public String getInfo() {
        return info;
    }



    public void setInfo(String info) {
        this.info = info;
    }

    public Request getRequest() {
        if (info != null)
            logger.info(info);
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Map<AssertType, Object> getAssertions() {
        return assertions;
    }

    public void setAssertions(Map<AssertType, Object> assertions) {
        this.assertions = assertions;
    }

    public Map<String, Object> getExtractions() {
        return extractions;
    }

    public void setExtractions(Map<String, Object> extractions) {
        this.extractions = extractions;
    }

    public void extract(ResponseContainer response, Map<String, Object> extractionMap) throws Exception {
        if (extractions == null)
            return;
        Map<String, Object> responseMap = GsonUtils.fromJsonObject(response.getResponseContent(), Map.class);
        if (extractions != null)
            new JsonExtractor().go(extractions, responseMap, extractionMap);
    }

    @Override
    public String toString() {
        return request.toString();
    }

    @Override
    public boolean evaluate(ResponseContainer responseContainer) {
        if (assertions == null)
            return false;
        for (Map.Entry<AssertType, Object> entry : assertions.entrySet())
            entry.getKey().setExpectedValue(entry.getValue()).evaluate(responseContainer);
        return false;
    }

    @Override
    public Boolean execute(ResponseContainer responseContainer) {
        return evaluate(responseContainer);
    }
}
