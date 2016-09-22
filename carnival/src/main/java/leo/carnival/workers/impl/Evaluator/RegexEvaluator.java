package leo.carnival.workers.impl.Evaluator;

import leo.carnival.workers.prototype.Evaluator;

import java.util.regex.Pattern;


@SuppressWarnings("WeakerAccess")
public class RegexEvaluator implements Evaluator<String> {

    private RegexEvaluator regexEvaluator;
    private String regex = ".*";

    @Override
    public boolean evaluate(String str) {
        return Pattern.matches(regex, str) && (regexEvaluator == null || regexEvaluator.evaluate(str));
    }

    @Override
    public Boolean execute(String s) {
        return evaluate(s);
    }

    public RegexEvaluator setRegex(String regex) {
        if (regex == null)
            return this;

        this.regex = regex;
        return this;
    }

    public RegexEvaluator setRegexEvaluator(RegexEvaluator regexEvaluator) {
        this.regexEvaluator = regexEvaluator;
        return this;
    }
}
