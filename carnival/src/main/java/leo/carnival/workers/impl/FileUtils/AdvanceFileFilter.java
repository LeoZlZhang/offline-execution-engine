package leo.carnival.workers.impl.FileUtils;


import leo.carnival.workers.baseType.Processor;
import leo.carnival.workers.baseType.Setter.WorkerSetter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leozhang on 8/26/16.
 * Search specific file in specific folder
 */
public final class AdvanceFileFilter implements Processor<File, List<File>>, WorkerSetter<Processor<File, List<File>>, Processor> {

    private FileFilter fileFilter;
    private FolderFilter folderFilter;

    @Override
    public List<File> process(File file) {
        if (file == null || !file.exists())
            return null;

        try {
            fileFilter = fileFilter == null ? FileFilter.build() : fileFilter;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            folderFilter = folderFilter == null ? FolderFilter.build() : folderFilter;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        List<File> rtnList = new ArrayList<>();

        for (File folder : folderFilter.process(file))
            rtnList.addAll(fileFilter.process(folder));


        return rtnList;
    }

    @Override
    public List<File> execute(File file) {
        return process(file);
    }

    @Override
    public AdvanceFileFilter setWorker(Processor<File, List<File>> worker) {
        if (worker instanceof FileFilter)
            fileFilter = (FileFilter) worker;
        if (worker instanceof FolderFilter)
            folderFilter = (FolderFilter) worker;
        return this;
    }

    public static AdvanceFileFilter build() throws IllegalAccessException, InstantiationException {
        return new AdvanceFileFilter();
    }

}
