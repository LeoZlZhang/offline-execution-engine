package com.vipabc.vliveshow.apitest.Util;

import com.vipabc.vliveshow.apitest.Util.Processor.JsonPathCollector;
import leo.carnival.workers.impl.Executors;
import leo.carnival.workers.impl.JacksonUtils;
import org.testng.Assert;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class JsonAssert extends AbstractJsonComparator<JsonPathCollector> {


    public void go(Map<String, Object> actual, Map<String, Object> expected) {
        handleMap(expected, actual, new JsonPathCollector());
    }


    @Override
    protected void preCheckForHandleMap(Map<String, Object> leftMap, Map<String, Object> rightMap, JsonPathCollector processor) {
//        Assert.assertTrue((leftMap.size() == 0 && rightMap.size() == 0) || (leftMap.size() > 0 && rightMap.size() > 0 && rightMap.size() >= leftMap.size()),
        Assert.assertTrue((rightMap.size() > 0 && rightMap.size() >= leftMap.size()) || (rightMap.size() == 0 && leftMap.size() == 0),
                String.format("[%d] Expected json structure is different from actual: [%s->%s]",
                        Thread.currentThread().getId(),
                        JacksonUtils.toJson(rightMap),
                        JacksonUtils.toJson(leftMap)));
    }

    @Override
    protected void preCheckForHandleList(List leftList, List rightList, JsonPathCollector processor) {
//        Assert.assertTrue(leftList.size() == rightList.size(), String.format("[%d] [JsonAssert] Expected list is different than actual: [%s->%s]",
        Assert.assertTrue(leftList.size() <= rightList.size(), String.format("[%d] Expected list is different than actual: [%s->%s]",
                Thread.currentThread().getId(), JacksonUtils.toJson(rightList), JacksonUtils.toJson(leftList)));
    }

    @Override
    protected void preCheckForRedirect(Object leftObject, Object rightObject, JsonPathCollector processor) {

    }

    @Override
    protected void ending(Object leftObject, Object rightObject, JsonPathCollector processor) {
        logger.info(String.format("[%d] Assert %s:[%s->%s]",
                Thread.currentThread().getId(),
                processor.getJsonPath(),
                rightObject instanceof Number ? numberParser.execute(rightObject) : rightObject,
                leftObject instanceof Number ? numberParser.execute(leftObject) : leftObject));

        if (leftObject instanceof String) {
            String expect = String.valueOf(leftObject);
            String actual = (rightObject instanceof String || rightObject instanceof Boolean) ?
                    String.valueOf(rightObject) : (rightObject instanceof Number) ?
                    numberParser.execute(rightObject).toString() : null;

            if (actual == null)
                Assert.fail(String.format("[%d] Actual json structure is different from expected [%s->%s]",
                        Thread.currentThread().getId(), JacksonUtils.toJson(rightObject), JacksonUtils.toJson(leftObject)));

            if (expect.contains("{{ACTUAL}}"))
                Assert.assertTrue((boolean) Executors.scriptExecutor().execute(expect.replaceAll("\\{\\{ACTUAL\\}\\}", actual)), String.format("Evaluate fail, Expected true:%s\n", expect));
            else
                Assert.assertTrue(Pattern.matches(expect, actual), String.format("Evaluate fail, Expected:%s, Actual:%s\n", expect, actual));

        } else if (leftObject instanceof Boolean)
            Assert.assertEquals(rightObject, leftObject);
        else if (leftObject instanceof Number)
            Assert.assertEquals(leftObject, rightObject);

    }

    @Override
    protected boolean isEnd(Object leftObject, Object rightObject, JsonPathCollector processor) {
        return leftObject instanceof String || leftObject == null || leftObject instanceof Boolean || leftObject instanceof Number;
    }


}
