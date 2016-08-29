package leo.carnival.workers.implementation.FileUtils.Evaluator;

import leo.carnival.workers.prototype.Evaluator;
import leo.carnival.workers.prototype.Setter.EvaluatorSetter;

import java.util.regex.Pattern;


@SuppressWarnings("WeakerAccess")
public final class RegexEvaluator implements Evaluator<String>, EvaluatorSetter<Evaluator<String>> {

    private Evaluator<String> evaluator;
    private String regex = ".*";

    @Override
    public boolean evaluate(String str) {
        return Pattern.matches(regex, str) && (evaluator == null || evaluator.evaluate(str));
    }

    @Override
    public Boolean execute(String s) {
        return evaluate(s);
    }

    @Override
    public RegexEvaluator setWorker(Evaluator<String> worker) {
        if (worker == null)
            return this;

        this.evaluator = worker;
        return this;
    }

    public RegexEvaluator setRegex(String regex) {
        if (regex == null)
            return this;

        this.regex = regex;
        return this;
    }

    public static RegexEvaluator build() throws IllegalAccessException, InstantiationException {
        return RegexEvaluator.class.newInstance();
    }

    public static RegexEvaluator build(String regex) throws InstantiationException, IllegalAccessException {
        return RegexEvaluator.build().setRegex(regex);
    }

    public static RegexEvaluator build(RegexEvaluator regexEvaluator) throws InstantiationException, IllegalAccessException {
        return RegexEvaluator.build().setWorker(regexEvaluator);
    }
}
