package com.vipabc.vliveshow.apitest.Util;


import com.vipabc.vliveshow.apitest.Util.Processor.JsonPathCollector;
import leo.carnival.workers.impl.GsonUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked", "WeakerAccess"})
public abstract class AbstractJsonComparator<T extends JsonPathCollector> {
    protected static final Logger logger = Logger.getLogger(AbstractJsonComparator.class);
    protected static final NumberFormat numberFormat = NumberFormat.getNumberInstance();

    protected abstract void preCheckForHandleMap(Map<String, Object> leftMap, Map<String, Object> rightMap, T processor);

    protected void handleMap(Map<String, Object> leftMap, Map<String, Object> rightMap, T processor) {
        preCheckForHandleMap(leftMap, rightMap, processor);
        for (String key : leftMap.keySet()) {
            Assert.assertTrue(rightMap.containsKey(key), String.format("[%d] [JsonAssert] Not found \"%s\" element in json response: :\"%s\"",
                    Thread.currentThread().getId(), key, GsonUtils.toJson(rightMap)));
            processor.process("{\"" + key + "\"");
            String path = processor.getJsonPath();
            processor.setJsonPath(path + "{\"" + key + "\"");
            redirect(leftMap.get(key), rightMap.get(key), processor);
            processor.setJsonPath(path);
        }
    }

    protected abstract void preCheckForHandleList(List leftList, List rightList, T processor);

    protected void handleList(List leftList, List rightList, T processor) {
        preCheckForHandleList(leftList, rightList, processor);
        Assert.assertTrue(leftList.size() <= rightList.size(), String.format("[%d] [JsonAssert] Actual list is shorter than expected: [%s->%s]",
                Thread.currentThread().getId(), GsonUtils.toJson(rightList), GsonUtils.toJson(leftList)));
        for (int i = 0, len = leftList.size(); i < len; i++) {
            String path = processor.getJsonPath();
            processor.setJsonPath(path + "[" + String.valueOf(i) + "]");
            redirect(leftList.get(i), rightList.get(i), processor);
            processor.setJsonPath(path);
        }
    }

    protected abstract void preCheckForRedirect(Object leftObject, Object rightObject, T processor);

    protected void redirect(Object leftObject, Object rightObject, T processor) {
        preCheckForRedirect(leftObject, rightObject, processor);
        if (isEnd(leftObject, rightObject, processor))
            ending(leftObject, rightObject, processor);

        else if (leftObject instanceof Map) {
            Assert.assertTrue(rightObject instanceof Map);
            handleMap((Map<String, Object>) leftObject, (Map<String, Object>) rightObject, processor);

        } else if (leftObject instanceof List) {
            Assert.assertTrue(rightObject instanceof List);
            handleList((List) leftObject, (List) rightObject, processor);

        } else
            Assert.fail(String.format("[%d] [JsonAssert] Found unsupported class: %s", Thread.currentThread().getId(), leftObject.getClass()));
    }

    protected abstract void ending(Object leftObject, Object rightObject, T processor);

    protected abstract boolean isEnd(Object leftObject, Object rightObject, T processor);



    protected Number parserNumber(Object obj) {
        try {
            return numberFormat.parse(obj.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
