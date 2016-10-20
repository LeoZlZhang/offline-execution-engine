package com.vipabc.vliveshow.apitest.bean.asset.assertions;

import com.vipabc.vliveshow.apitest.bean.asset.ResponseContainer;
import leo.carnival.workers.prototype.Evaluator;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by leozhang on 9/25/16.
 * Bean of assert type
 */
public class Assertion extends LinkedHashMap<AssertType, Object> implements Evaluator<ResponseContainer> {
    private Logger logger = Logger.getLogger(Assertion.class);

    @Override
    public boolean evaluate(ResponseContainer responseContainer) {
        if (isEmpty())
            return false;

        for (Map.Entry<AssertType, Object> entry : entrySet())
            entry.getKey()
                    .setLogger(logger)
                    .setExpectedValue(entry.getValue())
                    .evaluate(responseContainer);

        return false;
    }

    @Override
    public Boolean execute(ResponseContainer responseContainer) {
        return evaluate(responseContainer);
    }

    public Logger getLogger() {
        return logger;
    }

    public Assertion setLogger(Logger logger) {
        this.logger = logger;
        return this;
    }
}
