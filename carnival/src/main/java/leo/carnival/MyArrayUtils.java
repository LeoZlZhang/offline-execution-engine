package leo.carnival;


import java.lang.reflect.Array;
import java.util.Arrays;

@SuppressWarnings({"unchecked", "unused"})
public class MyArrayUtils {

    public static <T> T[] trimToSize(T[] oriArray) {
        if (oriArray == null)
            return null;
        return Arrays.copyOf(oriArray, sizeOfNotNull(oriArray));
    }

    public static <T> T[] mergeArray(T[] oriArray, T... ele) {
        if (oriArray == null)
            return null;

        if (ele == null)
            return oriArray;

        Class<T> cls = (Class<T>) oriArray.getClass().getComponentType();

        int sizeOfNotNull = sizeOfNotNull(oriArray);
        int sizeOfOriArray = oriArray.length;
        int sizeOfNotNull_ele = sizeOfNotNull(ele);

        if (sizeOfNotNull + sizeOfNotNull_ele <= sizeOfOriArray) {
            System.arraycopy(ele, 0, oriArray, sizeOfNotNull, sizeOfNotNull_ele);
            return oriArray;

        } else {
            T[] rtnArray = Arrays.copyOf(oriArray, sizeOfNotNull + sizeOfNotNull_ele);
            System.arraycopy(ele, 0, rtnArray, sizeOfNotNull, sizeOfNotNull_ele);
            return rtnArray;
        }
    }


    public static <T> boolean containElementByDeepCompare(T[] oriArray, T target) {
        for (T ele : oriArray) {
            if (ele == target)
                return true;
        }
        return false;
    }

    public static <T> T searchArray(T[] array, String name) {
        for (T obj : array)
            if (obj.toString().contains(name))
                return obj;
        return null;
    }

    public static <T> int getElementIndexInArray(T[] array, T t) {
        for (int i = 0, len = array.length; i < len; i++)
            if (array[i] == t)
                return i;
        return -1;
    }


    private static <T> int sizeOfNotNull(T[] array) {
        if (array == null || array.length == 0)
            return 0;

        int sizeofNotNull = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] != null) {
                sizeofNotNull = i + 1;
                break;
            }
        }
        return sizeofNotNull;
    }

}
