package leo.carnival.worker.filter;


import java.io.File;

public class FolderFilter extends FileFilter {

    public FolderFilter(String... regex) {
        super(regex);
    }

    @Override
    public File process(File file) {
        if (file.isDirectory())
            return evaluate(file) ? file : null;
        else
            return null;
    }

}
