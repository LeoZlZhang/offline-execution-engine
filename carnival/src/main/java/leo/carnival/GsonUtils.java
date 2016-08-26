package leo.carnival;

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

    public static <T> T firstOneFromJson(File file, Class<T> cls) throws IOException {
        T[] ts = fromJson(file, cls);
        return ts == null ? null : ts.length == 0 ? null : ts[0];
    }

    public static <T> T firstOneFromJson(String str, Class<T> cls) throws IOException {
        T[] ts = fromJson(str, cls);
        return ts == null ? null : ts.length == 0 ? null : ts[0];
    }


    public static <T> T[] fromJson(File sourceFile, Class<T> cls) throws IOException {
        if (sourceFile == null || !sourceFile.exists())
            return null;
        return fromJson(FileUtils.readFileToString(sourceFile), cls);
    }

    public static <T> T[] fromJson(String sourceStr, Class<T> cls) throws IOException {
        if (sourceStr == null)
            return null;
        switch (jsonType(sourceStr)) {
            case 1:
                T t = gson.fromJson(sourceStr, cls);
                T[] ts = (T[]) Array.newInstance(cls, 1);
                ts[0] = t;
                return ts;
            case 2:
                return (T[]) gson.fromJson(sourceStr, Array.newInstance(cls, 0).getClass());
            default:
                return null;
        }
    }

    private static <T> T fromJson(InputStream is, Class cls) {
        JsonReader reader = new JsonReader(new InputStreamReader(is));
        return gson.fromJson(reader, cls);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    private static int jsonType(String str) {
        if (isJsonObject(str))
            return 1;
        else if (isJsonArray(str))
            return 2;
        else
            return 0;
    }

    private static boolean isJsonObject(String str) {
        try {
            new JSONObject(str);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    private static boolean isJsonArray(String str) {
        try {
            new JSONArray(str);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }
}
