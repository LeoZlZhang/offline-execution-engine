package leo.carnival.workers.evaluator;

import leo.carnival.workers.baseType.Evaluator;
import leo.carnival.workers.baseType.WorkerSetter;

import java.util.regex.Pattern;


public class RegexEvaluator implements Evaluator<String>, WorkerSetter<Evaluator<String>> {

    private Evaluator<String> evaluator;
    private String regex = ".*";

    @Override
    public boolean evaluate(String str) {
        return Pattern.matches(regex, str) && evaluator.evaluate(str);
    }

    @Override
    public Boolean execute(String s) {
        return evaluate(s);
    }

    @Override
    public void setWorker(Evaluator<String> worker) {
        if (worker == null)
            return;

        this.evaluator = worker;
    }

    public void setRegex(String regex) {
        if (regex == null)
            return;

        this.regex = regex;
    }
}
