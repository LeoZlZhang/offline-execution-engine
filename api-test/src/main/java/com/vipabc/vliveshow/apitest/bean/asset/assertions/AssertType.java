package com.vipabc.vliveshow.apitest.bean.asset.assertions;


import com.vipabc.vliveshow.apitest.Util.JsonAssert;
import com.vipabc.vliveshow.apitest.bean.asset.ResponseContainer;
import leo.carnival.workers.prototype.Evaluator;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings({"unused", "unchecked"})
public enum AssertType implements Evaluator<ResponseContainer> {

    /**
     * Format:
     * "StatusCode": 200
     */
    StatusCode {
        @Override
        public Boolean execute(ResponseContainer responseContainer) {
            return evaluate(responseContainer);
        }

        @Override
        public boolean evaluate(ResponseContainer response) {
            Assert.assertTrue(expectedValue instanceof Double);
            int expected = ((Double) expectedValue).intValue();
            loggingAssert("StatusCode", response.getStatusCode(), expected);
            Assert.assertEquals(response.getStatusCode(), expected);
            return true;
        }
    },

    /**
     * Format:
     * "ResponseJson": {
     * "success": "true",
     * "message": "",
     * "error_code": "0",
     * "results.existPhone": "true"
     * }
     */
    ResponseJson {
        @SuppressWarnings("unchecked")
        @Override
        public boolean evaluate(ResponseContainer response) {
            new JsonAssert().go((Map) response.getResponseObject(), (Map) expectedValue);
            return true;
        }

        @Override
        public Boolean execute(ResponseContainer responseContainer) {
            return evaluate(responseContainer);
        }
    },

    /**
     * Format:
     * "ResponseString": "value to check"
     */
    ResponseString {
        @Override
        public boolean evaluate(ResponseContainer response) {
            String expect = expectedValue.toString();
            String actual = response.getResponseObject().toString();
            logger.info(String.format("[%d] Assert [%s->%s]", Thread.currentThread().getId(), actual, expect));
            Assert.assertTrue(Pattern.matches(expect, actual), String.format("Evaluate fail, Expected:%s, Actual:%s\n", expect, actual));
            return true;
        }


        @Override
        public Boolean execute(ResponseContainer responseContainer) {
            return evaluate(responseContainer);
        }
    };


    protected static final Logger logger = Logger.getLogger(AssertType.class);
    protected Object expectedValue = new Object();

    public AssertType setExpectedValue(Object expectedValue) {
        this.expectedValue = expectedValue;
        return this;
    }

    protected void loggingAssert(String path, Object actual, Object expect) {
        logger.info(String.format("[%d] Assert %s [%s->%s]", Thread.currentThread().getId(), path, actual, expect));

    }
}
