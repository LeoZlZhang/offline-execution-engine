package com.vipabc.vliveshow.apitest.bean.asset.request.RrequestEntity.MultiPartEntity;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.MultipartContent;

import java.io.Serializable;

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
class TextBody implements Serializable{

    private String name;

    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    MultipartContent.Part process() {
        MultipartContent.Part part = new MultipartContent.Part();
        part.setEncoding(null);
        part.setHeaders(new HttpHeaders().set("Content-Disposition", String.format("form-data; name=\"%s\"", name)));
        part.setContent(ByteArrayContent.fromString(null, text));
        return part;
    }
}
