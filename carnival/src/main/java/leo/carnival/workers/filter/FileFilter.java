package leo.carnival.workers.filter;


import leo.carnival.workers.baseType.Processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileFilter implements Processor<File, List<File>> {
    private String[] regex;

    public FileFilter(String... regex) {
        this.regex = regex == null ? new String[0] : regex;
    }

    protected void evaluate(File file, List<File> rtnFileList) {
        for (String reg : regex) {
            if (!Pattern.matches(reg, file.getName()))
                return;
        }
        rtnFileList.add(file);
    }


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


    protected List<File> search(File searchStartFile, List<File> rtnFileList) {
        if (rtnFileList == null)
            return null;
        File[] files = searchStartFile.isDirectory() ? searchStartFile.listFiles() : new File[]{searchStartFile};
        files = files == null ? new File[0] : files;

        for (File file : files)
            if (file.isDirectory())
                search(file, rtnFileList);
            else
                evaluate(file, rtnFileList);

        return rtnFileList;
    }
}
