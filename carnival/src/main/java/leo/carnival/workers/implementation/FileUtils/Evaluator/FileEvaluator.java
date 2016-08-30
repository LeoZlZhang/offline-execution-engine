package leo.carnival.workers.implementation.FileUtils.Evaluator;

import leo.carnival.workers.prototype.Evaluator;
import leo.carnival.workers.prototype.Setter.EvaluatorSetter;

import java.io.File;

/**
 * Created by leo_zlzhang on 8/26/2016.
 * Evaluate file refer to file name
 */
public class FileEvaluator implements Evaluator<File>, EvaluatorSetter<RegexEvaluator> {

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

    @Override
    public FileEvaluator setWorker(RegexEvaluator regexEvaluator) {
        if (regexEvaluator == null)
            return this;

        this.regexEvaluator = regexEvaluator;
        return this;
    }

    public static FileEvaluator build() {
        return new FileEvaluator();
    }

    public static FileEvaluator build(RegexEvaluator regexEvaluator)  {
        return FileEvaluator.build().setWorker(regexEvaluator);
    }
}
