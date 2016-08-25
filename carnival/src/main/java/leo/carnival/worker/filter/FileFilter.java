package leo.carnival.worker.filter;


import leo.carnival.worker.baseType.Processor;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

public class FileFilter implements Processor<File, List<File>> {
    protected String[] regex;

    public FileFilter(String... regex) {
        this.regex = regex == null ? new String[0] : regex;
    }

    protected boolean evaluate(File file) {
        for (String reg : regex) {
            if (!Pattern.matches(reg, file.getName()))
                return false;
        }
        return true;
    }


    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Override
    public List<File> process(File targetFile) {
        if (targetFile == null || !targetFile.exists())
            return null;

        //prepare files
        File[] files = targetFile.isDirectory() ? targetFile.listFiles() : new File[]{targetFile};
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

    @Override
    public File execute(File file) {
        return process(file);
    }
}
