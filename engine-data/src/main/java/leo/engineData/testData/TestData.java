package leo.engineData.testData;

import java.util.Map;

@SuppressWarnings("unused")
public interface TestData
{

//    boolean validate(String titleName, String cell) throws Exception;
//
//    TestData getInstance();

    String getTestingFlow();


    TestData update(Map<String, Object> profile);

    String getSourceFileName();
    void setSourceFileName(String name);
}
