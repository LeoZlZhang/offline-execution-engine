package Engine.util;


public class ArrayUtil {
    public static <T> T searchArray(T[] targetArray, String name) {
        for (T obj : targetArray) {
            if (obj.toString().contains(name))
                return obj;
        }

        return null;
    }

    public static <T> int containArray(T[] oriArray, T element) {
        for (int i = 0, len = oriArray.length; i < len; i++) {
            T ele = oriArray[i];
            if (ele == element)
                return i;
        }
        return -1;
    }

    @SuppressWarnings("unused")
    public static <T> void appendArray(T[] targetArray, T[] sourceArray) {
        if (sourceArray.length > targetArray.length)
            return;
        int fillingIndex = 0;
        for (int index = 0, len = targetArray.length; index < len; index++) {
            if (targetArray[index] == null) {
                fillingIndex = index;
                break;
            }
        }
        for (int index = 0, len = Math.min(sourceArray.length, (targetArray.length - fillingIndex)); index < len; index++)
            targetArray[fillingIndex + index] = sourceArray[index];

    }

}
