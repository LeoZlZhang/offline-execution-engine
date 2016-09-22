package com.vipabc.vliveshow.apitest.Util;


import leo.carnival.workers.impl.GearicUtils.NumberParser;
import leo.carnival.workers.impl.JacksonUtils;
import leo.carnival.workers.impl.Processors;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked", "WeakerAccess"})
public abstract class AbstractJsonComparator {
    protected static final Logger logger = Logger.getLogger(AbstractJsonComparator.class);
    protected static final NumberParser numberParser = Processors.NumberParser();

    protected abstract void preCheckForHandleMap(Map<String, Object> leftMap, Map<String, Object> rightMap, JsonPathAppender appender);

    protected void handleMap(Map<String, Object> leftMap, Map<String, Object> rightMap, JsonPathAppender appender) {
        preCheckForHandleMap(leftMap, rightMap, appender);
        for (String key : leftMap.keySet()) {
            Assert.assertTrue(rightMap.containsKey(key), String.format("[%d] [JsonAssert] Not found \"%s\" element in json response: :\"%s\"",
                    Thread.currentThread().getId(), key, JacksonUtils.toJson(rightMap)));
            appender.append("{\"" + key + "\"");
            String path = appender.get();
            appender.set(path + "{\"" + key + "\"");
            redirect(leftMap.get(key), rightMap.get(key), appender);
            appender.set(path);
        }
    }

    protected abstract void preCheckForHandleList(List leftList, List rightList, JsonPathAppender processor);

    protected void handleList(List leftList, List rightList, JsonPathAppender appender) {
        preCheckForHandleList(leftList, rightList, appender);
        Assert.assertTrue(leftList.size() <= rightList.size(), String.format("[%d] [JsonAssert] Actual list is shorter than expected: [%s->%s]",
                Thread.currentThread().getId(), JacksonUtils.toJson(rightList), JacksonUtils.toJson(leftList)));
        for (int i = 0, len = leftList.size(); i < len; i++) {
            String path = appender.get();
            appender.set(path + "[" + String.valueOf(i) + "]");
            redirect(leftList.get(i), rightList.get(i), appender);
            appender.set(path);
        }
    }

    protected abstract void preCheckForRedirect(Object leftObject, Object rightObject, JsonPathAppender processor);

    protected void redirect(Object leftObject, Object rightObject, JsonPathAppender appender) {
        preCheckForRedirect(leftObject, rightObject, appender);
        if (isEnd(leftObject, rightObject, appender))
            ending(leftObject, rightObject, appender);

        else if (leftObject instanceof Map) {
            Assert.assertTrue(rightObject instanceof Map);
            handleMap((Map<String, Object>) leftObject, (Map<String, Object>) rightObject, appender);

        } else if (leftObject instanceof List) {
            Assert.assertTrue(rightObject instanceof List);
            handleList((List) leftObject, (List) rightObject, appender);

        } else
            Assert.fail(String.format("[%d] [JsonAssert] Found unsupported class: %s", Thread.currentThread().getId(), leftObject.getClass()));
    }

    protected abstract void ending(Object leftObject, Object rightObject, JsonPathAppender processor);

    protected abstract boolean isEnd(Object leftObject, Object rightObject, JsonPathAppender processor);



    public class JsonPathAppender {

        protected String jsonPath = "";

        public String get() {
            return jsonPath;
        }

        public void set(String jsonPath) {
            this.jsonPath = jsonPath;
        }

        public String append(String element) {
            return jsonPath + element;
        }


        private Map<String, Object> extractionMap;


        public void setExtractionMap(Map<String, Object> extractionMap) {
            this.extractionMap = extractionMap;
        }

        public Map<String, Object> getExtractionMap() {
            return extractionMap;
        }
    }

}
