package TestCaseFactory;


import com.vipabc.vliveshow.TestExecutionEngine.TestCase.TestCase;
import com.vipabc.vliveshow.TestExecutionEngine.TestCaseFactory.DataProvider.AbstractDataProvider;
import com.vipabc.vliveshow.TestExecutionEngine.Util.Worker.Processor;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
abstract public class AbstractTestCaseFactory implements Processor<TestCase, List<TestCase>>{
    abstract void setDataProvider(AbstractDataProvider... provider);

}
