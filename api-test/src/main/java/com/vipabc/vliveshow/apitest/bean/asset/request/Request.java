package com.vipabc.vliveshow.apitest.bean.asset.request;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson.JacksonFactory;
import com.vipabc.vliveshow.apitest.bean.asset.request.RrequestEntity.MultiPartEntity.MultiPartEntity;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@SuppressWarnings({"DefaultAnnotationParam", "unused"})
public class Request implements Serializable {
    private static final Logger logger = Logger.getLogger(Request.class);
    private static final HttpTransport httpTransport = new NetHttpTransport();
    //    private static final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8899));
//    private static final HttpTransport httpTransport = new NetHttpTransport.Builder().setProxy(proxy).build();
    private static final HttpRequestFactory requestFactory = httpTransport.createRequestFactory();

    private String method;

    private String url;

    private Map<String, String> param;

    private MultiPartEntity multiPartEntity;

    private Map<String, Object> jsonBody;


    public String getUrl() {
        return url;
    }

    public Map<String, String> getParam() {
        return param;
    }


    public HttpResponse process() throws IOException {
        String url = generateUrl();
        logger.info(String.format("[%d] %s", Thread.currentThread().getId(), url));

        HttpContent content = null;
        if (multiPartEntity != null)
            content = multiPartEntity.process();
        if (jsonBody != null)
            content = new JsonHttpContent(new JacksonFactory(), jsonBody);

        HttpRequest httpRequest = requestFactory.buildRequest(method, new GenericUrl(url), content);
        return httpRequest.execute();
    }


    private String generateUrl() throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder(url);

        if (param != null && param.size() > 0) {
            sb.append("?");
            for (Map.Entry<String, String> entry : param.entrySet())
                sb.append(String.format("%s=%s&", entry.getKey().trim(), URLEncoder.encode(entry.getValue(), "UTF-8")));
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }


    @Override
    public String toString() {
        return url;
    }


}
