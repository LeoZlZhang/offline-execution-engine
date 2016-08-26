package TestCaseFactory;


import TestCase.TestCase;
import TestCaseFactory.DataProvider.AbstractDataProvider;
import leo.carnival.workers.baseType.Processor;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
abstract public class AbstractTestCaseFactory implements Processor<TestCase, List<TestCase>> {
    abstract void setDataProvider(AbstractDataProvider... provider);

}
