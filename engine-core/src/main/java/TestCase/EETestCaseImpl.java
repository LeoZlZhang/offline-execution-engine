package TestCase;


import leo.carnival.workers.InstanceUpdater;

import java.util.Map;

public abstract class EETestCaseImpl implements TestCase {

    @Override
    public TestCase update(Map<String, Object> profile) throws Exception {
        return new InstanceUpdater<TestCase>(profile).process(this);
    }


}
