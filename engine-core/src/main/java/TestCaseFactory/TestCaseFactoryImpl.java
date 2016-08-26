package TestCaseFactory;



import TestCase.TestCase;
import TestCaseFactory.DataProvider.AbstractDataProvider;
import leo.carnival.workers.ListCloner;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class TestCaseFactoryImpl extends AbstractTestCaseFactory {

    private AbstractDataProvider[] providers;
    private ListCloner resultCloner;

    @Override
    public void setDataProvider(AbstractDataProvider... provider) {
        this.providers = provider;
    }

    public void setResultCloner(ListCloner processor) {
        this.resultCloner = processor;
    }

    @Override
    public List<TestCase> process(TestCase testCase) {
        if (providers == null)
            return new ArrayList<>(0);

        List<TestCase> tcs = new ArrayList<>();
        for (AbstractDataProvider dp : providers)
            tcs.addAll(dp.process(testCase));

        if (resultCloner != null)
            tcs = resultCloner.process(tcs);
        return tcs;
    }

    @Override
    public List<TestCase> execute(TestCase testCase) {
        return process(testCase);
    }
}
