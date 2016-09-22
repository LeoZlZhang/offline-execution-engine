package com.vipabc.vliveshow.apitest.Util;

import com.vipabc.vliveshow.apitest.Util.Processor.ExtractionProcessor;
import leo.carnival.workers.impl.GsonUtils;
import org.testng.Assert;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class JsonExtractor extends AbstractJsonComparator<ExtractionProcessor> {
    private static final NumberFormat numberFormat = NumberFormat.getNumberInstance();

    public void go(Map<String, Object> instructionMap, Map<String, Object> responseMap, Map<String, Object> extractionMap) {
        ExtractionProcessor processor = new ExtractionProcessor();
        processor.setExtractionMap(extractionMap);
        handleMap(instructionMap, responseMap, processor);
    }

    @Override
    protected void preCheckForHandleMap(Map<String, Object> leftMap, Map<String, Object> rightMap, ExtractionProcessor processor) {
        Assert.assertTrue(rightMap.size() >= leftMap.size(),
                String.format("[%d] Expected json structure is different from actual: [%s->%s]",
                        Thread.currentThread().getId(),
                        GsonUtils.toJson(rightMap),
                        GsonUtils.toJson(leftMap)));
    }

    @Override
    protected void preCheckForHandleList(List leftList, List rightList, ExtractionProcessor processor) {

    }

    @Override
    protected void preCheckForRedirect(Object leftObject, Object rightObject, ExtractionProcessor processor) {

    }

    @Override
    protected void ending(Object leftObject, Object rightObject, ExtractionProcessor processor) {
        Object extractedRB = rightObject instanceof Number ? parserNumber(rightObject) : rightObject;
        logger.info(String.format("[%d] Extract %s:[%s as %s]", Thread.currentThread().getId(), processor.getJsonPath(), extractedRB, leftObject));
        processor.getExtractionMap().put(String.valueOf(leftObject), extractedRB); //response object could be String, list, map
    }

    @Override
    protected boolean isEnd(Object leftObject, Object rightObject, ExtractionProcessor processor) {
        return leftObject instanceof String;
    }


}


