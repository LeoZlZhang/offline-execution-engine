package leo.offlineExectionEngine.TestProfile.Flow;

import com.vipabc.vliveshow.TestExecutionEngine.TestProfile.Step.GeneralStep;
import com.vipabc.vliveshow.TestExecutionEngine.TestProfile.Step.LoopStep;
import com.vipabc.vliveshow.TestExecutionEngine.TestProfile.Step.Step;
import com.vipabc.vliveshow.TestExecutionEngine.Util.EEArrayUtil;

@SuppressWarnings("unused")
public class Flow {
    private String name;
    private String sourceClass;
    private Step[] steps;
    private FlowType type;

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public String getSourceClass() {
        return sourceClass;
    }

    public Step[] getSteps() throws InstantiationException, IllegalAccessException {
        Step[] rtnSteps = new Step[steps.length];

        for (int i = 0, j = 0, len = steps.length; i < len; j++) {
            Step step = steps[i];
            if (step.getLoop() == Step.NoLooping)
                rtnSteps[j] = step.transferToChild(GeneralStep.class);
            else
                rtnSteps[j] = compressToLoopStep(steps, i, step.getLoop(), Step.NoLooping);

            i += rtnSteps[j].countInnerStepNum();
        }
        return EEArrayUtil.trimToSize(rtnSteps);
    }

    public FlowType getType() {
        return type;
    }


    private LoopStep compressToLoopStep(Step[] steps, int startIndex, int loopId, Integer... endLoopIds) throws InstantiationException, IllegalAccessException {
        Step[] rtnSteps = new Step[steps.length - startIndex]; // rest length
        for (int i = 0, len = steps.length; startIndex < len; i++, startIndex++) {
            if (EEArrayUtil.containElementByDeepCompare(endLoopIds, steps[startIndex].getLoop()))
                break;
            else if (steps[startIndex].getLoop() == loopId)
                rtnSteps[i] = steps[startIndex].transferToChild(GeneralStep.class);
            else
                rtnSteps[i] = compressToLoopStep(steps, startIndex, steps[startIndex].getLoop(), EEArrayUtil.appendArray(endLoopIds, Integer.class, loopId));
        }


        LoopStep rtnLoopStep = new LoopStep();
        rtnLoopStep.setSteps(EEArrayUtil.trimToSize(rtnSteps));
        rtnLoopStep.setLoopIndex(loopId);
        return rtnLoopStep;
    }


}
