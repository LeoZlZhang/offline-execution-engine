package testCase;


import leo.carnival.workers.impl.JsonUtils.InstanceUpdater;
import leo.carnival.workers.impl.JsonUtils.MapValueUpdater;

import java.util.Map;

public abstract class EETestCaseImpl implements TestCase {

    @Override
    public TestCase update(Map<String, Object> profile) throws Exception {
        return new InstanceUpdater<TestCase>().setWorker(new MapValueUpdater().setDecorator(profile)).process(this);
    }


}
