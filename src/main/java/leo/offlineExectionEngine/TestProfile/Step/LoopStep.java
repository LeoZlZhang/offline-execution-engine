package leo.offlineExectionEngine.TestProfile.Step;

import java.util.Map;

public class LoopStep extends Step {
    private Step[] steps;

    private int loopIndex;

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }

    public void setLoopIndex(int loopIndex) {
        this.loopIndex = loopIndex;
    }

    @Override
    public String getMethod() {
        return "LoopStep: " + loopIndex;
    }

    @Override
    public void execute(Map<String, Object> context, Object FlowInstance) throws Exception {
        boolean loop = true;
        while (loop) {
            for (Step step : steps) {
                logger.info(String.format("[%d][%s][%s]...", Thread.currentThread().getId(), getMethod(), step.getMethod()));
                step.execute(context, FlowInstance);

                if (context.containsKey(ConsistentSteps.BreakLoop.name()) && (Boolean) context.get(ConsistentSteps.BreakLoop.name())) {
                    loop = false;
                    context.remove(ConsistentSteps.BreakLoop.name());
                    break;
                }
            }
        }
    }

    @Override
    public int countInnerStepNum() {
        int count = 0;
        for (Step step : steps)
            count += step.countInnerStepNum();
        return count;
    }
}
