package leo.offlineExectionEngine.TestProfile.Gear;

import com.vipabc.vliveshow.TestExecutionEngine.TestCase.Annotation.AutoWired;
import com.vipabc.vliveshow.TestExecutionEngine.TestProfile.Flow.Flow;

public class Gear {
    @AutoWired
    private String name;

    @AutoWired
    private Flow[] flows;

    public String getName() {
        return name;
    }

    public Flow[] getFlows() {
        return flows;
    }


    @Override
    public String toString() {
        return name;
    }
}
