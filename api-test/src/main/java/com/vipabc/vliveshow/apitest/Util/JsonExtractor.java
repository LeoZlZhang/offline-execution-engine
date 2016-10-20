package com.vipabc.vliveshow.apitest.Util;

import leo.carnival.workers.impl.JacksonUtils;
import leo.carnival.workers.prototype.Executor;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonExtractor extends AbstractJsonComparator implements Executor<Map<String, Object>, Map<String, Object>>{

    private Map<String, Object> instruction;
    private Map<String, Object> extraction;

    @Override
    public Map<String, Object> execute(Map<String, Object> actual) {
        if(instruction == null || actual == null)
            throw new RuntimeException("Instruction map or actual map is null");

        if(extraction == null)
            extraction = new HashMap<>();

        JsonPathAppender appender = new JsonPathAppender();
        appender.setExtractionMap(extraction);
        handleMap(instruction, actual, appender);

        return extraction;
    }

    public JsonExtractor setInstruction(Map<String, Object> instructionMap){
        this.instruction = instructionMap;
        return this;
    }

    public JsonExtractor setExtraction(Map<String, Object> extractionMap){
        this.extraction = extractionMap;
        return this;
    }


    @Override
    protected void preCheckForHandleMap(Map<String, Object> leftMap, Map<String, Object> rightMap, JsonPathAppender appender) {
        Assert.assertTrue(rightMap.size() >= leftMap.size(),
                String.format("[%d] Expected json structure is different from actual: [%s->%s]",
                        Thread.currentThread().getId(),
                        JacksonUtils.toJson(rightMap),
                        JacksonUtils.toJson(leftMap)));
    }

    @Override
    protected void preCheckForHandleList(List leftList, List rightList, JsonPathAppender appender) {

    }

    @Override
    protected void preCheckForRedirect(Object leftObject, Object rightObject, JsonPathAppender appender) {

    }

    @Override
    protected void ending(Object leftObject, Object rightObject, JsonPathAppender appender) {
        Object extractedRB = rightObject instanceof Number ? numberParser.execute(rightObject) : rightObject;
        logger.info(String.format("[%d] Extract %s:[%s as %s]", Thread.currentThread().getId(), appender.get(), extractedRB, leftObject));
        appender.getExtractionMap().put(String.valueOf(leftObject), extractedRB); //response object could be String, list, map
    }

    @Override
    protected boolean isEnd(Object leftObject, Object rightObject, JsonPathAppender processor) {
        return leftObject instanceof String;
    }


    public JsonExtractor setLogger(Logger logger) {
        this.logger = logger;
        return this;
    }

}


