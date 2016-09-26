package com.vipabc.vliveshow.apitest.bean.asset.request.RrequestEntity;

import com.google.api.client.http.HttpContent;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson.JacksonFactory;
import leo.carnival.workers.prototype.Processor;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Created by leozhang on 9/25/16.
 * Bean of json body for post request
 */
public class JsonBody extends LinkedHashMap<String, Object> implements Processor<Object, HttpContent>, Serializable {
    @Override
    public HttpContent process(Object nil) {
        return new JsonHttpContent(new JacksonFactory(),  this);
    }

    @Override
    public HttpContent execute(Object nil) {
        return process(nil);
    }
}
