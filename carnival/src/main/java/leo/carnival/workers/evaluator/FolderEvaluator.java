package leo.carnival.workers.evaluator;

import leo.carnival.workers.baseType.Evaluator;
import leo.carnival.workers.baseType.WorkerSetter;

import java.io.File;

/**
 * Created by leo_zlzhang on 8/26/2016.
 * Evaluate folder refer to folder name
 */
@SuppressWarnings("unused")
public class FolderEvaluator implements Evaluator<File>, WorkerSetter<Evaluator> {

    protected Evaluator evaluator;

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean evaluate(File file) {
        if (file == null || !file.isDirectory())
            return false;

        if (evaluator instanceof RegexEvaluator)
            return ((RegexEvaluator) evaluator).evaluate(file.getName());

        return true;
    }

    @Override
    public Boolean execute(File file) {
        return evaluate(file);
    }

    @Override
    public void setWorker(Evaluator worker) {
        if (worker == null)
            return;
        this.evaluator = worker;
    }
}
