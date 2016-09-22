package leo.engineCore.engineFoundation.Flow;


import leo.carnival.MyArrayUtils;
import leo.carnival.workers.impl.GsonUtils;
import leo.carnival.workers.impl.Processors;
import leo.carnival.workers.prototype.Executor;
import leo.engineCore.engineFoundation.ApplicationContext;
import leo.engineCore.engineFoundation.Step.GeneralStep;
import leo.engineCore.engineFoundation.Step.LoopStep;
import leo.engineCore.engineFoundation.Step.Step;


@SuppressWarnings("unused")
public class Flow implements Executor<ApplicationContext, Flow> {
    private String sourceClass;
    private String name;
    private Step[] steps;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Flow execute(ApplicationContext applicationContext) {
        loadStepsOnce();
        applicationContext.setFlowName(name);
        applicationContext.setMethodRepo(Processors.ClassLoader().process(sourceClass));
        for (Step step : steps)
            step.execute(applicationContext);
        return this;
    }



    private void loadStepsOnce() {
        if (steps == null || steps.length == 0 || steps[0].getClass().getSuperclass() == Step.class)
            return;

        try {
            Step[] rtnSteps = new Step[steps.length];

            for (int i = 0, j = 0, len = steps.length; i < len; j++) {
                Step step = steps[i];
                if (step.getLoop() == Step.NoLooping)
                    rtnSteps[j] = Processors.ClassDecorator(GeneralStep.class).process(GsonUtils.toJson(step));
                else
                    rtnSteps[j] = compressToLoopStep(steps, i, step.getLoop(), Step.NoLooping);

                i += rtnSteps[j].countInnerStepNum();
            }
            steps = MyArrayUtils.trimToSize(rtnSteps);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private LoopStep compressToLoopStep(Step[] steps, int startIndex, int loopId, Integer... endLoopIds) throws InstantiationException, IllegalAccessException {
        Step[] rtnSteps = new Step[steps.length - startIndex];

        for (int i = 0, len = steps.length; startIndex < len; i++, startIndex++) {
            if (MyArrayUtils.containElementByDeepCompare(endLoopIds, steps[startIndex].getLoop()))
                break;
            else if (steps[startIndex].getLoop() == loopId)
                rtnSteps[i] = Processors.ClassDecorator(GeneralStep.class).process(GsonUtils.toJson(steps[startIndex]));
            else
                rtnSteps[i] = compressToLoopStep(steps, startIndex, steps[startIndex].getLoop(), MyArrayUtils.mergeArray(endLoopIds, loopId));
        }

        LoopStep rtnLoopStep = new LoopStep();
        rtnLoopStep.setSteps(MyArrayUtils.trimToSize(rtnSteps));
        rtnLoopStep.setLoopIndex(loopId);
        return rtnLoopStep;
    }
}
