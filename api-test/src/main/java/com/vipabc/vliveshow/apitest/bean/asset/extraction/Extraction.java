package com.vipabc.vliveshow.apitest.bean.asset.extraction;

import com.vipabc.vliveshow.apitest.Util.JsonExtractor;
import com.vipabc.vliveshow.apitest.bean.asset.ResponseContainer;
import leo.carnival.workers.prototype.Processor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by leozhang on 9/25/16.
 * Bean of extraction
 */
@SuppressWarnings("unchecked")
public class Extraction extends LinkedHashMap<String, Object> implements Processor<ResponseContainer, Map<String, Object>> {

    private Map<String, Object> extractionMap;

    @Override
    public Map<String, Object> process(ResponseContainer responseContainer) {
        if (isEmpty())
            return null;
        if (extractionMap == null)
            throw new RuntimeException("Miss extractionMap to store extracted Object!");
        if (responseContainer == null || responseContainer.getResponseObject() == null || !(responseContainer.getResponseObject() instanceof Map))
            throw new RuntimeException("Invalid response object!");

        new JsonExtractor().setExtraction(extractionMap).setInstruction(this).execute((Map) responseContainer.getResponseObject());

        return extractionMap;
    }

    @Override
    public Map<String, Object> execute(ResponseContainer responseContainer) {
        return process(responseContainer);
    }


    public Extraction setExtractionMap(Map<String, Object> extractionMap) {
        this.extractionMap = extractionMap;
        return this;
    }
}
