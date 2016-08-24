package leo.offlineExectionEngine.Util.Filter;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearchUtil {


    public static File findFirstFile(String searchStartFolderPath, Filter filter) {
        return findFirstFile(new File(searchStartFolderPath), filter);
    }

    public static File findFirstFile(File searchStartFolder, Filter filter) {
        List<File> returnList = findAllFile(searchStartFolder, filter);
        assert returnList != null && returnList.size() > 0;
        return returnList.get(0);
    }


    public static List<File> findAllFile(String searchStartFolderPath, Filter filter) {
        return findAllFile(new File(searchStartFolderPath), filter);
    }

    public static List<File> findAllFile(File searchStartFolder, Filter filter) {
        List<File> returnList = new ArrayList<>();
        findAllFile(searchStartFolder, returnList, filter);
        return returnList;
    }


    static boolean findAllFile(File searchStartFolder, List<File> returnList, Filter filter) {
        if (returnList == null || filter == null)
            return false;

        File[] allFiles = searchStartFolder.isDirectory() ? searchStartFolder.listFiles() : new File[]{searchStartFolder};
        for (File file : allFiles != null ? allFiles : new File[0]) {
            if (filter.evaluate(file)) { //hit
                returnList.addAll(filter.process(file));
            } else if (file.isDirectory())
                findAllFile(file, returnList, filter);
        }
        return !returnList.isEmpty();
    }

}
