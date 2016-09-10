package com.vipabc.vliveshow.apitest.bean.asset.assertions;


import com.vipabc.vliveshow.apitest.Util.JsonAssert;
import com.vipabc.vliveshow.apitest.bean.asset.ResponseContainer;
import leo.carnival.workers.impl.JsonUtils.GsonUtils;
import leo.carnival.workers.prototype.Evaluator;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.Map;

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

            Assert.assertTrue(expectedValue instanceof Map);
            Map<String, Object> expected = (Map) expectedValue;

            Map<String, Object> actual = GsonUtils.fromJsonObject(response.getResponseContent(), Map.class);
            new JsonAssert().go(actual, expected);
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
        logger.info(String.format("[%d] Assert [%s] [%s->%s]", Thread.currentThread().getId(), path, actual, expect));

    }
}
