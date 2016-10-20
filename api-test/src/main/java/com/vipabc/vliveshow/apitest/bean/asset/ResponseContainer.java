package com.vipabc.vliveshow.apitest.bean.asset;

import com.google.api.client.http.HttpResponse;
import leo.carnival.workers.impl.JacksonUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings({"WeakerAccess", "unchecked"})
public class ResponseContainer {
    private Logger logger = Logger.getLogger(ResponseContainer.class);
    private Map responseObject;
    private HttpResponse response;

    private ResponseContainer(HttpResponse response, Logger logger) {
        try {
            this.response = response;
            this.responseObject = JacksonUtils.fromJson(response.parseAsString(), Map.class);

            logger.info(String.format("[%d] Response: %s", Thread.currentThread().getId(), JacksonUtils.toPrettyJson(responseObject)));
//            logger.info(String.format("[%d] Response: %s", Thread.currentThread().getId(), JacksonUtils.toJson(responseObject)));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseContainer(String responseObject) {
        if (JacksonUtils.isJsonObject(responseObject))
            this.responseObject = JacksonUtils.fromJson(responseObject, Map.class);
        else {
            this.responseObject = new LinkedHashMap<String, String>();
            this.responseObject.put("result", responseObject == null ? "" : responseObject);
        }

        logger.info(String.format("[%d] Response: %s", Thread.currentThread().getId(), JacksonUtils.toPrettyJson(this.responseObject)));
//        logger.info(String.format("[%d] Response: %s", Thread.currentThread().getId(), JacksonUtils.toJson(this.responseObject)));
    }


    public static ResponseContainer build(HttpResponse response) {
        return new ResponseContainer(response);
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }
}
