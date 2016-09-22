package leo.carnival.workers.impl.FileUtils;


import leo.carnival.workers.impl.Processors;
import leo.carnival.workers.prototype.Processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leozhang on 8/26/16.
 * Search specific file in specific folder
 */
@SuppressWarnings("WeakerAccess")
public final class FileFilterAdvance implements Processor<File, List<File>> {

    private FileFilter fileFilter;
    private FolderFilter folderFilter;

    @Override
    public List<File> process(File file) {
        if (file == null || !file.exists())
            return null;

        fileFilter = fileFilter == null ? Processors.FileFilter(null) : fileFilter;
        folderFilter = folderFilter == null ? Processors.FolderFilter(null) : folderFilter;

        List<File> rtnList = new ArrayList<>();

        for (File folder : folderFilter.process(file))
            rtnList.addAll(fileFilter.process(folder));


        return rtnList;
    }


    @Override
    public List<File> execute(File file) {
        return process(file);
    }


    public FileFilterAdvance setFileFilter(FileFilter fileFilter) {
        if (fileFilter == null)
            return this;

        this.fileFilter = fileFilter;
        return this;
    }


    public FileFilterAdvance setFolderFilter(FolderFilter folderFilter) {
        if (folderFilter == null)
            return this;

        this.folderFilter = folderFilter;
        return this;
    }
}
