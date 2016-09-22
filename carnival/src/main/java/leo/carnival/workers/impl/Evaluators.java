package leo.carnival.workers.impl;

import leo.carnival.workers.impl.Evaluator.FileEvaluator;
import leo.carnival.workers.impl.Evaluator.FolderEvaluator;
import leo.carnival.workers.impl.Evaluator.MethodEvaluator;
import leo.carnival.workers.impl.Evaluator.RegexEvaluator;

/**
 * Created by leo_zlzhang on 9/22/2016.
 * Container of evaluator
 */
public class Evaluators {

    public static RegexEvaluator RegexEvaluator(String regex) {
        return new RegexEvaluator().setRegex(regex == null ? "" : regex);
    }

    public static FileEvaluator FileEvaluator(RegexEvaluator regexEvaluator) {
        return new FileEvaluator().setRegexEvaluator(regexEvaluator);
    }

    public static FolderEvaluator FolderEvaluator(RegexEvaluator regexEvaluator) {
        return new FolderEvaluator().setRegexEvaluator(regexEvaluator);
    }


    public static MethodEvaluator MethodEvaluator(String methodName, Integer paramLength){
        return new MethodEvaluator().setExpectedMethodName(methodName).setExpectedParamLength(paramLength);
    }

    private static final Evaluators evaluators = new Evaluators();

    private Evaluators() {
    }

    public static Evaluators build() {
        return evaluators;
    }
}
