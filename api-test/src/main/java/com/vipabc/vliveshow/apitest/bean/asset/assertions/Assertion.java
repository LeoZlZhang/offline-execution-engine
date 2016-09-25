package com.vipabc.vliveshow.apitest.bean.asset.assertions;

import com.vipabc.vliveshow.apitest.bean.asset.ResponseContainer;
import leo.carnival.workers.prototype.Evaluator;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by leozhang on 9/25/16.
 * Bean of assert type
 */
public class Assertion extends LinkedHashMap<AssertType, Object> implements Evaluator<ResponseContainer> {
    @Override
    public boolean evaluate(ResponseContainer responseContainer) {
        if (isEmpty())
            return false;

        for (Map.Entry<AssertType, Object> entry : entrySet())
            entry.getKey().setExpectedValue(entry.getValue()).evaluate(responseContainer);

        return false;
    }

    @Override
    public Boolean execute(ResponseContainer responseContainer) {
        return evaluate(responseContainer);
    }
}
