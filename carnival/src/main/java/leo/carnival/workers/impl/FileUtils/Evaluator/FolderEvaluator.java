package leo.carnival.workers.impl.FileUtils.Evaluator;

import leo.carnival.workers.baseType.Evaluator;
import leo.carnival.workers.baseType.Setter.EvaluatorSetter;

import java.io.File;

/**
 * Created by leo_zlzhang on 8/26/2016.
 * Evaluate folder refer to folder name
 */

public final class FolderEvaluator implements Evaluator<File>, EvaluatorSetter<RegexEvaluator> {

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

    @Override
    public FolderEvaluator setWorker(RegexEvaluator worker) {
        if (worker == null)
            return this;

        this.evaluator = worker;
        return this;
    }

    public static FolderEvaluator build() throws IllegalAccessException, InstantiationException {
        return FolderEvaluator.class.newInstance();
    }

    public static FolderEvaluator build(RegexEvaluator regexEvaluator) throws InstantiationException, IllegalAccessException {
        return FolderEvaluator.build().setWorker(regexEvaluator);
    }
}
