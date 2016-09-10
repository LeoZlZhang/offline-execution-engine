package com.vipabc.vliveshow.apitest.bean.asset;

import com.google.api.client.http.HttpResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ResponseContainer {
    private Logger logger = Logger.getLogger(ResponseContainer.class);
    private String responseContent;
    private HttpResponse response;

    public ResponseContainer(HttpResponse response) throws IOException {
        this.response = response;
        this.responseContent = getResponseContent(response);
    }

    public String getResponseContent() {
        return responseContent;
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    private String getResponseContent(HttpResponse response) throws IOException {
        String responseBody = response.parseAsString();
        logger.info(String.format("[%d] Response: %s", Thread.currentThread().getId(), responseBody));
        return responseBody;
    }

}
