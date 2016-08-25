package leo.carnival.workers.filter;


import java.io.File;
import java.util.List;

public class FolderFilter extends FileFilter {

    public FolderFilter(String... regex) {
        super(regex);
    }

    @Override
    protected List<File> search(File searchStartFile, List<File> rtnFileList) {
        if (rtnFileList == null)
            return null;
        File[] files = searchStartFile.isDirectory() ? searchStartFile.listFiles() : new File[]{searchStartFile};
        files = files == null ? new File[0] : files;

        for (File file : files)
            if (file.isDirectory()) {
                evaluate(file, rtnFileList);
                search(file, rtnFileList);
            }

        return rtnFileList;
    }


}
