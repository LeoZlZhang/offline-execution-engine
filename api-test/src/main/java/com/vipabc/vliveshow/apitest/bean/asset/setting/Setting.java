package com.vipabc.vliveshow.apitest.bean.asset.setting;


import leo.carnival.workers.impl.Executors;
import leo.carnival.workers.impl.GearicUtils.ScriptExecutor;
import leo.carnival.workers.prototype.Processor;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by leozhang on 9/25/16.
 * Bean of setting
 */
public class Setting extends LinkedHashMap<String, Object> implements Processor<Map<String, Object>, Map<String, Object>>, Serializable {

    private static final ScriptExecutor scriptExecutor = Executors.scriptExecutor();
    private static final Pattern jsPattern = Pattern.compile("\\[js\\[([\\s\\S]+)\\]\\]");

    @Override
    public Map<String, Object> process(Map<String, Object> extractionMap) {
        if (isEmpty())
            return extractionMap;

        for (Map.Entry<String, Object> map : entrySet()) {
            String str = map.getValue().toString();

            Matcher jsMatcher = jsPattern.matcher(str);
            while (jsMatcher.find()) {
                String oldValue = jsMatcher.group(0);
                String newValue = scriptExecutor.execute(jsMatcher.group(1)).toString();
                str = str.replace(oldValue, newValue);
            }
            extractionMap.put(map.getKey(), str);
        }

        return extractionMap;
    }

    @Override
    public Map<String, Object> execute(Map<String, Object> extractionMap) {
        return process(extractionMap);
    }
}
