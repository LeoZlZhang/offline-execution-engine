package leo.offlineExectionEngine.TestCaseFactory;


import com.vipabc.vliveshow.TestExecutionEngine.TestCase.TestCase;
import com.vipabc.vliveshow.TestExecutionEngine.TestCaseFactory.DataProvider.AbstractDataProvider;
import com.vipabc.vliveshow.TestExecutionEngine.Util.Worker.ListCloner;

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

}
