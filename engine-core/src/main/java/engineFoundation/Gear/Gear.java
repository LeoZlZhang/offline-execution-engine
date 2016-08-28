package engineFoundation.Gear;


import engineFoundation.Assert.TestResult;
import engineFoundation.Flow.Flow;
import leo.carnival.workers.baseType.Executor;
import testCase.TestCase;

public class Gear implements Executor<TestCase, TestResult>{
    private String name;

    private Flow[] flows;

    public String getName() {
        return name;
    }

    public Flow[] getFlows() {
        return flows;
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public TestResult execute(TestCase testCase) {
        return null;
    }
}
