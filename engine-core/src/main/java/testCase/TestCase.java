package testCase;

import java.util.Map;

@SuppressWarnings("unused")
public interface TestCase
{

    void fill(String titleName, String cell) throws Exception;

    <T> T get(String titleName, Class<T> cls) throws Exception;

    boolean validate(String titleName, String cell) throws Exception;

    TestCase getInstance();

    String getTestingFlow();

    String getSourceFileName();

    TestCase update(Map<String, Object> profile) throws Exception;

    void setSourceFileName(String name);
}
