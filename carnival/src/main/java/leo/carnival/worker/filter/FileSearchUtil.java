package leo.carnival.worker.filter;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearchUtil {


    public static File findFirstFile(File searchStartFolder, FileFilter filter) {
        List<File> returnList = findAllFile(searchStartFolder, filter);
        return returnList == null ? null : returnList.size() == 0 ? null : returnList.get(0);
    }


    public static List<File> findAllFile(File searchStartFolder, FileFilter filter) {
        List<File> returnList = new ArrayList<>();
        findAllFile(searchStartFolder, returnList, filter);
        return returnList;
    }


    static boolean findAllFile(File searchStartFolder, List<File> returnList, FileFilter filter) {
        if (searchStartFolder == null || !searchStartFolder.exists() || returnList == null || filter == null)
            return false;

        //prepare files
        File[] files = searchStartFolder.isDirectory() ? searchStartFolder.listFiles() : new File[]{searchStartFolder};
        files = files == null ? new File[0] : files;

        for (File file : files) {
            if(filter instanceof FolderFilter){
                File
            }
            List<File> fileList = filter.execute(file);
            if (fileList != null)
                returnList.addAll(fileList);
        }
        return true;
    }

}
