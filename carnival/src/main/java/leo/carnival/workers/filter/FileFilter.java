package leo.carnival.workers.filter;


import leo.carnival.workers.baseType.Processor;
import leo.carnival.workers.baseType.Worker;
import leo.carnival.workers.baseType.WorkerSetter;
import leo.carnival.workers.evaluator.FileEvaluator;
import leo.carnival.workers.evaluator.FolderEvaluator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileFilter implements Processor<File, List<File>>, WorkerSetter {

    protected FolderEvaluator folderEvaluator;
    protected FileEvaluator fileEvaluator;

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
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

    @Override
    public void setWorker(Worker worker) {
        if(worker instanceof FolderEvaluator)
            folderEvaluator = (FolderEvaluator) worker;
        if(worker instanceof FileEvaluator)
            fileEvaluator = (FileEvaluator) worker;
    }

    protected List<File> search(File searchStartFile, List<File> rtnFileList) {
        if (rtnFileList == null)
            return null;
        File[] files = searchStartFile.isDirectory() ? searchStartFile.listFiles() : new File[]{searchStartFile};
        files = files == null ? new File[0] : files;

        for (File file : files)
            if (file.isDirectory() && (folderEvaluator == null || folderEvaluator.evaluate(file)))
                search(file, rtnFileList);
            else if(fileEvaluator == null || fileEvaluator.evaluate(file))
                rtnFileList.add(file);

        return rtnFileList;
    }

    public static FileFilter build() throws InstantiationException, IllegalAccessException {
        return FileFilter.class.newInstance();
    }



}
