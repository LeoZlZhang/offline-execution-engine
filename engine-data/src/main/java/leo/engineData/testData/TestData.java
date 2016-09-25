package leo.engineData.testData;

import leo.carnival.workers.prototype.Executor;

import java.util.Map;

@SuppressWarnings("unused")
public interface TestData<T, G> extends Executor<T, G>
{

    String getTestingFlow();
    TestData update(Map<String, Object> profile);
    String getSourceFileName();
    void setSourceFileName(String name);
}
