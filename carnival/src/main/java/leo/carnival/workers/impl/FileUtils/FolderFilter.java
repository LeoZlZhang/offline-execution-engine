package leo.carnival.workers.impl.FileUtils;


import leo.carnival.workers.impl.Evaluator.FolderEvaluator;
import leo.carnival.workers.prototype.Processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class FolderFilter implements Processor<File, List<File>> {

    private FolderEvaluator folderEvaluator;

    @Override
    public List<File> process(File targetFile) {
        if (targetFile == null || !targetFile.exists())
            return null;

        List<File> rtnFileList = new ArrayList<>();
        return search(targetFile, rtnFileList);
    }

    @Override
    public List<File> execute(File file) {
        return process(file);
    }

    public FolderFilter setFolderEvaluator(FolderEvaluator evaluator) {
        folderEvaluator = evaluator;
        return this;
    }

    private List<File> search(File searchStartFile, List<File> rtnFileList) {
        if (rtnFileList == null)
            return null;
        File[] files = searchStartFile.isDirectory() ? searchStartFile.listFiles() : new File[]{searchStartFile};
        files = files == null ? new File[0] : files;

        for (File file : files)
            if (file.isDirectory()) {
                if (folderEvaluator == null || folderEvaluator.evaluate(file))
                    rtnFileList.add(file);
                search(file, rtnFileList);
            }

        return rtnFileList;
    }
}
