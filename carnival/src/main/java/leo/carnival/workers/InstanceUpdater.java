package leo.carnival.workers;


import com.google.gson.Gson;
import leo.carnival.workers.baseType.Processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unchecked")
public class InstanceUpdater<T> implements Processor<T, T> {

    private static final String regInjectionField = "(\\{\\{([\\w\\W]+?)}})";
    private static final Pattern patternInjectionField = Pattern.compile(regInjectionField);

    private Map<String, Object> referenceMap;


    public InstanceUpdater(Map<String, Object> referenceMap) {
        this.referenceMap = referenceMap == null ? new HashMap<>() : referenceMap;
    }


    @Override
    public T process(T targetInstance) {
        if (targetInstance == null)
            return null;

        Gson gson = new Gson();
        Map targetMap = gson.fromJson(gson.toJson(targetInstance), Map.class);

        updateMap(targetMap);

        return gson.fromJson(gson.toJson(targetMap), (Class<T>) targetInstance.getClass());
    }


    @Override
    public T execute(T targetInstance) {
        return process(targetInstance);
    }

    private Object updateObject(Object targetObj) {
        if (targetObj instanceof List)
            updateList((List) targetObj);
        else if (targetObj instanceof Map)
            updateMap((Map) targetObj);
        return targetObj;
    }

    private void updateMap(Map targetMap) {
        if (targetMap == null)
            return;
        for (Object key : targetMap.keySet()) {
            Object targetValue = targetMap.get(key);
            if (targetValue instanceof String)
                targetMap.put(key, updateString((String) targetValue));
            else
                updateObject(targetValue);
        }
    }

    private void updateList(List targetList) {
        if (targetList == null)
            return;
        for (int i = 0, len = targetList.size(); i < len; i++) {
            Object targetObject = targetList.get(i);
            if (targetList.get(i) instanceof String)
                targetList.set(i, updateString((String) targetObject));
            else
                updateObject(targetObject);
        }
    }

    @SuppressWarnings("DanglingJavadoc")
    private Object updateString(String str) {
        //Exactly matched
        /**
         * mean to replace String with object
         * open mind! set a object type of field as {{ABC}}, it will be transfered to and object
         * Not only support to replace string with string, but also support replace string with complex object
         */
        Matcher matcher = patternInjectionField.matcher(str);
        if (matcher.matches() && referenceMap.containsKey(matcher.group(2)))
            return referenceMap.get(matcher.group(2));

        //Partly matched
        //Replace field key word with String from map
        matcher = patternInjectionField.matcher(str);
        while (matcher.find())
            if (referenceMap.containsKey(matcher.group(2)))
                str = str.replace(matcher.group(1), String.valueOf(referenceMap.get(matcher.group(2))));

        return str;
    }
}
