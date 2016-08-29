package testData;


import leo.carnival.workers.implementation.JsonUtils.InstanceUpdater;
import leo.carnival.workers.implementation.JsonUtils.MapValueUpdater;

import java.util.Map;

@SuppressWarnings("unused")
public abstract class TestDataImpl implements TestData {

    @Override
    public TestData update(Map<String, Object> profile) throws Exception {
        return (TestData) InstanceUpdater.build().setWorker(MapValueUpdater.build(profile)).process(this);
    }

}
