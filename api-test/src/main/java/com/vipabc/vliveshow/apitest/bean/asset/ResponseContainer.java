package com.vipabc.vliveshow.apitest.bean.asset;

import com.google.api.client.http.HttpResponse;
import leo.carnival.workers.impl.JacksonUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;

@SuppressWarnings({"WeakerAccess", "unchecked"})
public class ResponseContainer {
    private static final Logger logger = Logger.getLogger(ResponseContainer.class);
    private Object responseObject;
    private HttpResponse response;

    public ResponseContainer(HttpResponse response) throws IOException {
        this.response = response;
        this.responseObject = getResponseMap(response);
    }

    public ResponseContainer(String responseObject) {
        if (JacksonUtils.isJsonObject(responseObject)) {
            this.responseObject = JacksonUtils.fromJsonObject(responseObject, Map.class);
            logger.info(String.format("[%d] Response: %s", Thread.currentThread().getId(), JacksonUtils.toPrettyJson(responseObject)));
        }else{
            this.responseObject =responseObject;
            logger.info(String.format("[%d] Response: %s", Thread.currentThread().getId(), responseObject));
        }

    }

    public Object getResponseObject() {
        return responseObject;
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    private Map<String, Object> getResponseMap(HttpResponse response) throws IOException {
        String responseString = response.parseAsString();
        Map<String, Object> responseContent = JacksonUtils.fromJsonObject(responseString, Map.class);
        logger.info(String.format("[%d] Response: %s", Thread.currentThread().getId(), JacksonUtils.toPrettyJson(responseString)));
        return responseContent;
    }

}
