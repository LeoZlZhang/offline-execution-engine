package leo.offlineExectionEngine.Util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;

public class GsonUtil
{
    public static <T> T toObject(File sourceFile, Class cls) throws IOException
    {
        InputStream is = new FileInputStream(sourceFile);
        T rtnInstance = toObject(is, cls);
        is.close();
        return rtnInstance;
    }
    public static <T> T toObject(String sourceStr, Class cls) throws IOException
    {
        InputStream is = new ByteArrayInputStream(sourceStr.getBytes());
        T rtnInstance = toObject(is, cls);
        is.close();
        return rtnInstance;
    }
    private static <T> T toObject(InputStream is, Class cls)
    {
        JsonReader reader = new JsonReader(new InputStreamReader(is));
        Gson myJson = new Gson();
        return myJson.fromJson(reader, cls);
    }

    public static String toString(Object obj){
        Gson myJson = new Gson();
        return myJson.toJson(obj);
    }
}
