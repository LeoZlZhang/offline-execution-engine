package leo.engineData.DataProvider;

import leo.carnival.workers.implementation.GearicUtils.ArrayClone;
import leo.engineData.testData.TestData;
import leo.carnival.MyArrayUtils;
import leo.carnival.workers.prototype.Processor;
import leo.carnival.workers.implementation.JsonUtils.GsonUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by leozhang on 8/28/16.
 * Load test case refer to files
 */
@SuppressWarnings({"unchecked", "WeakerAccess"})
public class JsonList2DataObjTransfer<T extends TestData> implements Processor<List<String>, T[]> {
    private Class<T> tcClass;
    private ArrayClone<T> arrayClone;

    @Override
    public T[] process(List<String> datas) {

        T[] rtnTCArray = (T[]) Array.newInstance(tcClass, datas.size() * 3);

        for (String data : datas) {
            T[] tcs = GsonUtils.fromJsonArray(data, tcClass);
            rtnTCArray = MyArrayUtils.mergeArray(rtnTCArray, tcs);
        }
        if (arrayClone == null)
            return MyArrayUtils.trimToSize(rtnTCArray);
        else
            return arrayClone.process(MyArrayUtils.trimToSize(rtnTCArray));
    }

    @Override
    public T[] execute(List<String> files) {
        return process(files);
    }

    public JsonList2DataObjTransfer<T> setClassOfTestCase(Class<T> cls) {
        this.tcClass = cls;
        return this;
    }

    public JsonList2DataObjTransfer<T> setCloneWorker(ArrayClone<T> arrayClone) {
        this.arrayClone = arrayClone;
        return this;
    }

    public static <T extends TestData> JsonList2DataObjTransfer<T> build(Class<T> cls) {
        return new JsonList2DataObjTransfer<T>().setClassOfTestCase(cls);
    }

}
