package leo.offlineExectionEngine.Util.Filter;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileFilter implements Filter {
    private String[] regex;

    public FileFilter(String... regex) {
        this.regex = regex;
    }

    @Override
    public boolean evaluate(File file) {
        for (String reg : regex) {
            if (reg == null || reg.isEmpty() || !Pattern.matches(reg, file.getName()))
                return false;
        }
        return true;

    }



    @Override
    public List<File> process(File file) {
        List<File> rtnList = new ArrayList<>();
        if (file.isDirectory())
            FileSearchUtil.findAllFile(file, rtnList, new FileFilter());
        else
            rtnList.add(file);
        return rtnList;
    }
}
