package leo.carnival;


import com.google.gson.Gson;
import leo.carnival.annotations.AutoWired;
import leo.carnival.workers.baseType.Processor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstanceUpdater<T> implements Processor<T, T> {

    private static final String regInjectionField = "(\\{\\{([\\w\\W]+?)}})";
    private static final Pattern patternInjectionField = Pattern.compile(regInjectionField);

    protected Map<String, Object> referenceMap;


    public InstanceUpdater(Map<String, Object> referenceMap) {
        this.referenceMap = referenceMap == null ? new HashMap<>() : referenceMap;
    }


    @Override
    public T process(T targetInstance) {
        if (targetInstance == null)
            return targetInstance;

        for (Field field : targetInstance.getClass().getDeclaredFields()) {
            if (field.getAnnotation(AutoWired.class) == null || !field.getAnnotation(AutoWired.class).allowUpdate())
                continue;
            if (field.getType().isPrimitive())
                continue;

            try {
                field.setAccessible(true);
                Object value = field.get(targetInstance);

                if (value == null)
                    continue;

                if (value instanceof String)
                    field.set(targetInstance, fetchValueFromProfile(value, referenceMap));

                else if (isDataStructure(value))
                    updateDataStructure(value, profile);

                else
                    update(value, profile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public T execute(T targetInstance) {
        if (targetInstance == null)
            return null;

        Gson gson = new Gson();
        String targetJsonString = gson.toJson(targetInstance);
        Map targetMap = gson.fromJson(targetJsonString, Map.class);

        updateMap(targetMap);

        targetJsonString = gson.toJson(targetMap);
        return gson.fromJson(targetJsonString, );
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
        for (int i = 0, len = targetList.size(); i < len; i++){
            Object targetOjbect  = targetList.get(i);
            if(targetList.get(i) instanceof String)
                targetList.set(i, updateString((String) targetOjbect));
            else
                updateObject(targetOjbect);
        }
    }

    private String updateString(String targetStr) {

        return "";
    }


    private static Object fetchValueFromProfile(Object fieldValue, Map<String, Object> profile) {
        String str = String.valueOf(fieldValue);
        //Replace field value to object from map
        Matcher matcher = patternInjectionField.matcher(str);
        if (matcher.matches() && profile.containsKey(matcher.group(2))) {
            return profile.get(matcher.group(2));
        }

        //Replace field key word with String from map
        matcher = patternInjectionField.matcher(str);
        while (matcher.find()) {
            if (profile.containsKey(matcher.group(2)))
                str = str.replace(matcher.group(1), String.valueOf(profile.get(matcher.group(2))));
        }
        return str;
    }


    private static boolean isDataStructure(Object obj) {
        return obj instanceof Object[] || obj instanceof List || obj instanceof Map;
    }

    @SuppressWarnings("unchecked")
    private static void updateDataStructure(Object value, Map<String, Object> profile) throws Exception {

        if (value instanceof Object[]) {
            Object[] array = (Object[]) value;
            for (int i = 0, len = array.length; i < len; i++) {
                if (array[i] instanceof String)
                    array[i] = fetchValueFromProfile(array[i], profile);
                else if (isDataStructure(array[i]))
                    updateDataStructure(array[i], profile);
                else
                    update(array[i], profile);
            }
        } else if (value instanceof List) {
            List list = (List) value;
            for (int i = 0, len = list.size(); i < len; i++) {
                if (list.get(i) instanceof String)
                    list.set(i, fetchValueFromProfile(list.get(i), profile));
                else if (isDataStructure(list.get(i)))
                    updateDataStructure(list.get(i), profile);
                else
                    update(list.get(i), profile);
            }
        } else if (value instanceof Map) {
            Map map = (Map) value;
            for (Object key : map.keySet()) {
                if (map.get(key) instanceof String)
                    map.put(key, fetchValueFromProfile(map.get(key), profile));
                else if (isDataStructure(map.get(key)))
                    updateDataStructure(map.get(key), profile);
                else
                    update(map.get(key), profile);
            }
        } else
            throw new Exception("Unsupported type:" + value.getClass().toString());

    }


}
