package leo.carnival;


import com.vipabc.vliveshow.TestExecutionEngine.TestCase.Annotation.AutoWired;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateInstanceUtil {

    private static final String regInjectionField = "(\\{\\{([\\w\\W]+?)}})";
    private static final Pattern patternInjectionField = Pattern.compile(regInjectionField);

    public static <T> void update(T targetInstance, Map<String, Object> profile) throws Exception {
        if (targetInstance == null)
            return;
        for (Field field : targetInstance.getClass().getDeclaredFields()) {
            if (field.getAnnotation(AutoWired.class) == null || !field.getAnnotation(AutoWired.class).allowUpdate())
                continue;
            if (field.getType().isPrimitive())
                continue;
            field.setAccessible(true);
            Object value = field.get(targetInstance);

            if (value == null)
                continue;

            if (value instanceof String)
                field.set(targetInstance, fetchValueFromProfile(value, profile));

            else if (isDataStructure(value))
                updateDataStructure(value, profile);

            else
                update(value, profile);

        }
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
