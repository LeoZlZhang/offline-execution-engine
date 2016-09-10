package com.vipabc.vliveshow.apitest.Util.Processor;

import java.util.Map;


public class ExtractionProcessor extends JsonPathCollector{
    private Map<String, Object> extractionMap;


    public void setExtractionMap(Map<String, Object> extractionMap) {
        this.extractionMap = extractionMap;
    }

    public Map<String, Object> getExtractionMap() {
        return extractionMap;
    }
}
