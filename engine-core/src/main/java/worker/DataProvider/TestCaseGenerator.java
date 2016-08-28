package worker.DataProvider;

import testCase.TestCase;
import leo.carnival.MyArrayUtils;
import leo.carnival.workers.baseType.Processor;
import leo.carnival.workers.impl.JsonUtils.GsonUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by leozhang on 8/28/16.
 * Load test case refer to files
 */
@SuppressWarnings("unchecked")
public class TestCaseGenerator<T extends TestCase> implements Processor<List<String>, T[]>{
    private Class<T> tcClass;
    @Override
    public T[] process(List<String> datas) {

        T[] rtnTCArray = (T[]) Array.newInstance(tcClass, 0);

        for(String data : datas){
            try {
                T[] tcs = GsonUtils.fromJson(data, tcClass);
                rtnTCArray = MyArrayUtils.appendArray(rtnTCArray, tcs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rtnTCArray;
    }

    @Override
    public T[] execute(List<String> files) {
        return process(files);
    }

    public TestCaseGenerator<T> setClassOfTestCase(Class<T> cls) {
        this.tcClass =  cls;
        return this;
    }

    public static <T extends TestCase> TestCaseGenerator<T> build(Class<T> cls) {
        return new TestCaseGenerator<T>().setClassOfTestCase(cls);
    }

}
