package leo.carnival.workers.impl.JsonUtils;

import leo.carnival.workers.impl.Executors;
import leo.carnival.workers.impl.GearicUtils.ScriptExecutor;
import leo.carnival.workers.prototype.Processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by leozhang on 8/27/16.
 * Decorate any value need to be decorated in mapA whose key is contained in mapB(decorator)
 * decorated value in mapA formats as {{value}}
 */
@SuppressWarnings({"unchecked", "WeakerAccess"})
public class MapValueUpdater implements Processor<Map, Map> {
    private static final String regInjectionField = "(\\{\\{([\\w\\W]+?)}})";
    private static final Pattern patternInjectionField = Pattern.compile(regInjectionField);
    private static final String jsReg = "\\[js\\[([\\s\\S]+)\\]\\]";

    private Map<String, Object> decorator = new HashMap<>();
    private ScriptExecutor scriptEngine;

    @Override
    public Map process(Map map) {
        return updateMap(map);
    }

    @Override
    public Map execute(Map map) {
        return process(map);
    }


    public MapValueUpdater setDecorator(Map<String, Object> decorator) {
        if (decorator == null)
            return this;

        this.decorator = decorator;
        return this;
    }

    public MapValueUpdater setScriptEngine(ScriptExecutor scriptEngine){
        this.scriptEngine = scriptEngine;
        return this;
    }
    
    public static MapValueUpdater build(Map<String, Object> decorator) {
        return new MapValueUpdater().setDecorator(decorator);
    }


    private Object updateObject(Object targetObj) {
        if (targetObj instanceof List)
            updateList((List) targetObj);
        else if (targetObj instanceof Map)
            updateMap((Map) targetObj);
        return targetObj;
    }


    private Map updateMap(Map targetMap) {
        if (targetMap == null)
            return null;

        for (Object key : targetMap.keySet()) {
            Object targetValue = targetMap.get(key);
            if (targetValue instanceof String)
                targetMap.put(key, updateString((String) targetValue));
            else
                updateObject(targetValue);
        }
        return targetMap;
    }

    private List updateList(List targetList) {
        if (targetList == null)
            return null;

        for (int i = 0, len = targetList.size(); i < len; i++) {
            Object targetObject = targetList.get(i);
            if (targetList.get(i) instanceof String)
                targetList.set(i, updateString((String) targetObject));
            else
                updateObject(targetObject);
        }
        return targetList;
    }

    @SuppressWarnings("DanglingJavadoc")
    private Object updateString(String str) {
        /**
         * mean to replace String with object
         * open mind! set a object type of field as string {{key}}, it will be transferred to and object
         * Not only support to replace string with string, but also support replace string with complex object
         */
        //Exactly matched
        Matcher matcher = patternInjectionField.matcher(str);
        if (matcher.matches() && decorator.containsKey(matcher.group(2)))
            return decorator.get(matcher.group(2));

        //Partly matched
        matcher = patternInjectionField.matcher(str);
        while (matcher.find())
            if (decorator.containsKey(matcher.group(2)))
                str = str.replace(matcher.group(1), String.valueOf(decorator.get(matcher.group(2))));

        if(scriptEngine!= null){
            Matcher jsMatcher = Pattern.compile(jsReg).matcher(str);
            while (jsMatcher.find()) {
                String oldValue = jsMatcher.group(0);
                String newValue = Executors.scriptExecutor().execute(jsMatcher.group(1)).toString();
                str = str.replace(oldValue, newValue);
            }
        }
        return str;
    }
}
