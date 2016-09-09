package leo.engineData.testData;

import leo.carnival.workers.prototype.Processor;

import java.util.Map;

@SuppressWarnings("unused")
public interface TestData<T, G> extends Processor<T, G>
{

//    boolean validate(String titleName, String cell) throws Exception;
//
//    TestData getInstance();

    String getTestingFlow();


    TestData update(Map<String, Object> profile);

    String getSourceFileName();
    void setSourceFileName(String name);
}
