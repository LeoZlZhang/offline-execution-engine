package leo.carnival.workers.filter;


import java.io.File;
import java.util.List;

public class FolderFilter extends FileFilter {


    @Override
    protected List<File> search(File searchStartFile, List<File> rtnFileList) {
        if (rtnFileList == null)
            return null;
        File[] files = searchStartFile.isDirectory() ? searchStartFile.listFiles() : new File[]{searchStartFile};
        files = files == null ? new File[0] : files;

        for (File file : files)
            if (file.isDirectory())
                if (folderEvaluator == null || folderEvaluator.evaluate(file))
                    rtnFileList.add(file);
                else
                    search(file, rtnFileList);

        return rtnFileList;
    }


    public static FolderFilter build() throws InstantiationException, IllegalAccessException {
        return FolderFilter.class.newInstance();
    }

}
