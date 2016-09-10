package com.vipabc.vliveshow.apitest.Util.Processor;


import leo.carnival.workers.prototype.Processor;

public class JsonPathCollector implements Processor<String, String> {

    protected String jsonPath = "";

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    @Override
    public String process(String s) {
        return jsonPath + s;
    }

    @Override
    public String execute(String s) {
        return process(s);
    }
}
