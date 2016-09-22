package leo.carnival.workers.impl.Evaluator;

import leo.carnival.workers.prototype.Evaluator;

import java.io.File;

/**
 * Created by leo_zlzhang on 8/26/2016.
 * Evaluate file refer to file name
 */
public class FileEvaluator implements Evaluator<File> {

    private RegexEvaluator regexEvaluator;

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean evaluate(File file) {
        if (file == null || !file.isFile())
            return false;

        return regexEvaluator == null || regexEvaluator.evaluate(file.getName());
    }

    @Override
    public Boolean execute(File file) {
        return evaluate(file);
    }

    public FileEvaluator setRegexEvaluator(RegexEvaluator regexEvaluator) {
        if (regexEvaluator == null)
            return this;

        this.regexEvaluator = regexEvaluator;
        return this;
    }
}
