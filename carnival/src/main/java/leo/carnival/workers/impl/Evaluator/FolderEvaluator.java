package leo.carnival.workers.impl.Evaluator;

import leo.carnival.workers.prototype.Evaluator;

import java.io.File;

/**
 * Created by leo_zlzhang on 8/26/2016.
 * Evaluate folder refer to folder name
 */

public final class FolderEvaluator implements Evaluator<File> {

    private RegexEvaluator evaluator;

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean evaluate(File file) {
        if (file == null || !file.isDirectory())
            return false;

        return evaluator == null || evaluator.evaluate(file.getName());
    }

    @Override
    public Boolean execute(File file) {
        return evaluate(file);
    }

    public FolderEvaluator setRegexEvaluator(RegexEvaluator regexEvaluator) {
        if (regexEvaluator == null)
            return this;

        this.evaluator = regexEvaluator;
        return this;
    }
}
