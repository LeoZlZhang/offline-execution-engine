package leo.engineData.testData;


import leo.carnival.workers.impl.JsonUtils.InstanceUpdater;
import leo.carnival.workers.impl.JsonUtils.MapValueUpdater;

import java.util.Map;

@SuppressWarnings("unused")
public abstract class TestData<T, G> extends Bean<T, G>{


    private String workflow;
    private String sourceFileName;

    public TestData update(Map<String, Object> profile) {
        return (TestData) InstanceUpdater.build().setWorker(MapValueUpdater.build(profile)).process(this);
    }

    /**
     * Getter
     */
    public String getWorkflow() {
        return workflow;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    /**
     * Setter
     */
    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }
}
