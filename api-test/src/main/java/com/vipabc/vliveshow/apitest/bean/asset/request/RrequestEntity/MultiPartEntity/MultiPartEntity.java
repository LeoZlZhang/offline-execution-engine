package com.vipabc.vliveshow.apitest.bean.asset.request.RrequestEntity.MultiPartEntity;


import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.http.MultipartContent;
import leo.carnival.workers.prototype.Processor;

import java.io.Serializable;


@SuppressWarnings({"unused", "DefaultAnnotationParam", "MismatchedReadAndWriteOfArray"})
public class MultiPartEntity implements Processor<Object[], HttpContent>, Serializable{
    private TextBody[] textBodies;

    private BinaryBody[] binaryBodies;


    public TextBody[] getTextBodies() {
        return textBodies;
    }

    public void setTextBodies(TextBody[] textBodies) {
        this.textBodies = textBodies;
    }

    public BinaryBody[] getBinaryBodies() {
        return binaryBodies;
    }

    public void setBinaryBodies(BinaryBody[] binaryBodies) {
        this.binaryBodies = binaryBodies;
    }

    @Override
    public HttpContent process(Object... nullObject) {
        MultipartContent multipartContent = new MultipartContent();
        multipartContent.setMediaType(new HttpMediaType("multipart/form-data").setParameter("boundary", "__END_OF_PART__"));

        if (textBodies != null) {
            for (TextBody textBody : textBodies)
                multipartContent.addPart(textBody.process());
        }
        if (binaryBodies != null) {
            for (BinaryBody binaryBody : binaryBodies)
                multipartContent.addPart(binaryBody.process());
        }
        return multipartContent;
    }

    @Override
    public HttpContent execute(Object[] objects) {
        return process(objects);
    }
}
