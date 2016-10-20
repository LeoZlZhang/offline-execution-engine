package com.vipabc.vliveshow.apitest.bean.asset.request.RrequestEntity;

import leo.carnival.workers.prototype.Processor;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by leozhang on 9/24/16.
 * Bean of request param
 */
public class RequestParam extends LinkedHashMap<String, String> implements Processor<String, String>, Serializable {


    @Override
    public String process(String url) {
        StringBuilder sb = new StringBuilder(url);

        try {
            if (size() > 0) {
                sb.append("?");
                for (Map.Entry<String, String> entry : entrySet())
                    sb.append(String.format("%s=%s&", entry.getKey().trim(), URLEncoder.encode(entry.getValue(), "UTF-8")));
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String execute(String url) {
        return process(url);
    }
}
