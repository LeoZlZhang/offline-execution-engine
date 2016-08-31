package leo.carnival.workers.impl.JsonUtils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Array;

@SuppressWarnings({"unused", "WeakerAccess", "unchecked"})
public final class GsonUtils {
    private static final Gson gson = new Gson();

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
        return fromJsonArray(FileUtils.readFileToString(jsonFile), cls);
    }

    public static <T> T[] fromJsonArray(String sourceStr, Class<T> cls){
        if (sourceStr == null)
            return null;

        return (T[]) gson.fromJson(sourceStr, Array.newInstance(cls, 0).getClass());
    }

    public static <T> T fromJsonObject(File jsonFile, Class<T> cls) throws IOException {
        if (jsonFile == null || !jsonFile.exists())
            return null;
        return fromJsonObject(FileUtils.readFileToString(jsonFile), cls);
    }

    public static <T> T fromJsonObject(String sourceStr, Class<T> cls) {
        if (sourceStr == null)
            return null;

        return gson.fromJson(sourceStr, cls);

    }

    private static <T> T fromJson(InputStream is, Class<T> cls) {
        JsonReader reader = new JsonReader(new InputStreamReader(is));
        return gson.fromJson(reader, cls);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
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
