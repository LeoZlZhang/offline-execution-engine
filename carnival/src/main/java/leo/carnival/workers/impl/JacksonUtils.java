package leo.carnival.workers.impl;


import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

@SuppressWarnings({"unused", "WeakerAccess", "unchecked"})
public final class JacksonUtils {
    private static final ObjectMapper mapper = new ObjectMapper().disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);

    public static <T> T firstOneFromJsonArray(File jsonFile, Class<T> cls) throws IOException {
        T[] ts = fromJsonArray(jsonFile, cls);
        return ts == null ? null : ts.length == 0 ? null : ts[0];
    }


    public static <T> T firstOneFromJsonArray(String str, Class<T> cls) {
        T[] ts = fromJsonArray(str, cls);
        return ts == null ? null : ts.length == 0 ? null : ts[0];
    }


    public static <T> T[] fromJsonArray(File jsonFile, Class<T> cls) throws IOException {
        if (jsonFile == null || !jsonFile.exists())
            return null;
        try {
            return (T[]) mapper.readValue(jsonFile, Array.newInstance(cls, 0).getClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T[] fromJsonArray(String sourceStr, Class<T> cls) {
        if (sourceStr == null)
            return null;

        try {
            return (T[]) mapper.readValue(sourceStr, Array.newInstance(cls, 0).getClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T fromJsonObject(File jsonFile, Class<T> cls) throws IOException {
        if (jsonFile == null || !jsonFile.exists())
            return null;
        try {
            return mapper.readValue(jsonFile, cls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T fromJsonObject(String sourceStr, Class<T> cls) {
        if (sourceStr == null)
            return null;

        try {
            return mapper.readValue(sourceStr, cls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static <T> T fromJson(InputStream is, Class<T> cls) {
        try {
            return mapper.readValue(is, cls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toPrettyJson(Object obj) {
        try {
            if (obj instanceof String)
                obj = mapper.readValue(obj.toString(), Object.class);
            return "\r\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean isJsonObject(String str) {
        try {
            new JSONObject(str);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static boolean isJsonArray(String str) {
        try {
            new JSONArray(str);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }
}
