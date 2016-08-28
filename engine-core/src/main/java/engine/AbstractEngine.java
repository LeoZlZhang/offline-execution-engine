package engine;



import testCase.TestCase;
import engineFoundation.Assert.TestFail;
import engineFoundation.Assert.TestResult;
import engineFoundation.Gear.Gear;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("unused")
public abstract class AbstractEngine
{
    protected Map<String, Object> context = new HashMap<>();
    protected Gear gear;

    public AbstractEngine()
    {
        context.put("TestResult", new TestFail("No test result."));
    }

    public abstract void loadGear(File gearFile);
    public abstract TestResult execute(String flowName);
    public abstract TestResult execute(TestCase testcase);
    public abstract TestResult execute(Map<String, Object> context, String flowName, Object... obj);


    public abstract <T> T getExecuteInfo(String key, Class<T> cla);
    public abstract String getExecuteInfo(String key);




}
