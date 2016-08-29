package engineFoundation.Step;

import engineFoundation.ApplicationContext;

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
    public LoopStep execute(ApplicationContext applicationContext) {
        Map<String, Object> context = applicationContext.getContext();
        boolean loop = true;
        while (loop) {
            for (Step step : steps) {
                step.execute(applicationContext);

                if (context.containsKey("BreakLoop") && (Boolean) context.get("BreakLoop")) {
                    loop = false;
                    context.remove("BreakLoop");
                    break;
                }
            }
        }
        return this;
    }

    @Override
    public int countInnerStepNum() {
        int count = 0;
        for (Step step : steps)
            count += step.countInnerStepNum();
        return count;
    }
}
