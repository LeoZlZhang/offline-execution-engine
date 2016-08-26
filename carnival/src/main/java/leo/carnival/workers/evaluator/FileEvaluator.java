package leo.carnival.workers.evaluator;

import java.io.File;

/**
 * Created by leo_zlzhang on 8/26/2016.
 * Evaluate file refer to file name
 */
public class FileEvaluator extends FolderEvaluator{

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean evaluate(File file) {
        if (file == null || !file.isFile())
            return false;

        if (evaluator instanceof RegexEvaluator)
            return ((RegexEvaluator) evaluator).evaluate(file.getName());

        return true;
    }
}
