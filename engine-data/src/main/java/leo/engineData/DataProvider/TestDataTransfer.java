package leo.engineData.DataProvider;

import leo.carnival.MyArrayUtils;
import leo.carnival.workers.impl.GearicUtils.ArrayClone;
import leo.carnival.workers.impl.GsonUtils;
import leo.carnival.workers.prototype.Processor;
import leo.engineData.testData.TestData;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Map;

/**
 * Created by leozhang on 8/28/16.
 * Load test case refer to files
 */
@SuppressWarnings({"unchecked", "WeakerAccess"})
public class TestDataTransfer<T extends TestData> implements Processor<Map<File, String>, T[]> {
    private Class<T> tcClass;
    private ArrayClone<T> arrayClone;
    private static final int estimatedCaseNumPerFile = 3;

    @Override
    public T[] process(Map<File, String> datas) {
        if(datas == null ||datas.isEmpty())
            return null;

        T[] rtnTCArray = (T[]) Array.newInstance(tcClass, datas.size() * estimatedCaseNumPerFile);

        for (Map.Entry<File, String> data : datas.entrySet()) {
            T[] tds = GsonUtils.fromJsonArray(data.getValue(), tcClass);
            for(TestData td : tds)
                td.setSourceFileName(data.getKey().getName());
            rtnTCArray = MyArrayUtils.mergeArray(rtnTCArray, tds);
        }
        if (arrayClone == null)
            return MyArrayUtils.trimToSize(rtnTCArray);
        else
            return arrayClone.process(MyArrayUtils.trimToSize(rtnTCArray));
    }

    @Override
    public T[] execute(Map<File, String> datas) {
        return process(datas);
    }

    public TestDataTransfer<T> setClassOfTestCase(Class<T> cls) {
        this.tcClass = cls;
        return this;
    }

    public TestDataTransfer<T> setCloneWorker(ArrayClone<T> arrayClone) {
        this.arrayClone = arrayClone;
        return this;
    }

    public static <T extends TestData> TestDataTransfer<T> build(Class<T> cls) {
        return new TestDataTransfer<T>().setClassOfTestCase(cls);
    }

}
