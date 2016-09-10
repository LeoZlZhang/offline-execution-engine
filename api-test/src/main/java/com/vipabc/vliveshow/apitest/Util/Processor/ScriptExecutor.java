package com.vipabc.vliveshow.apitest.Util.Processor;

import leo.carnival.workers.prototype.Processor;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by leo_zlzhang on 9/9/2016.
 * Execute java script
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ScriptExecutor implements Processor<String, Object> {
    private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

    private ScriptExecutor() {
    }

    @Override
    public Object process(String script) {
        try {
            return engine.eval(script);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object execute(String script) {
        return process(script);
    }


    public static ScriptExecutor build() {
        return new ScriptExecutor();
    }


}
