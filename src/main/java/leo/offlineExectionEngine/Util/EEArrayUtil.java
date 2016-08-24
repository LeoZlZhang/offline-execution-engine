package leo.offlineExectionEngine.Util;


import java.lang.reflect.Array;
import java.util.Arrays;

public class EEArrayUtil {

    public static <T> T[] trimToSize(T[] oriArray) {
        int lengthUtilAllNull = 0;
        for (int i = oriArray.length - 1; i >= 0; i--) {
            if (oriArray[i] != null) {
                lengthUtilAllNull = i + 1;
                break;
            }
        }
        return Arrays.copyOf(oriArray, lengthUtilAllNull);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] appendArray(T[] oriArray, Class<T> cls, T... ele) {
        int sizeofNotNull = 0;
        for (int i = oriArray.length - 1; i >= 0; i--) {
            if (oriArray[i] != null) {
                sizeofNotNull = i + 1;
                break;
            }
        }

        T[] rtnArray = (T[]) Array.newInstance(cls, sizeofNotNull + ele.length);
        if (sizeofNotNull > 0)
            System.arraycopy(oriArray, 0, rtnArray, 0, sizeofNotNull);
        if (ele.length > 0)
            System.arraycopy(ele, 0, rtnArray, sizeofNotNull, ele.length);

        return rtnArray;
    }

    public static <T> boolean containElementByDeepCompare(T[] oriArray, T target) {
        for (T ele : oriArray) {
            if (ele == target)
                return true;
        }
        return false;
    }


}
