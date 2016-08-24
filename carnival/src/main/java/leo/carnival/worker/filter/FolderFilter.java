package leo.carnival.worker.filter;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class FolderFilter implements Filter {
    private String[] regex;

    public FolderFilter(String... regex) {
        this.regex = regex;
    }

    @Override
    public boolean evaluate(File file) {
        if (file.isDirectory()) {
            for (String reg : regex) {
                if (reg != null && !reg.isEmpty() && Pattern.matches(reg, file.getName()))
                    return true;
            }
        }
        return false;
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Override
    public List<File> process(File file) {
        return new ArrayList<>(Arrays.asList(file));
    }
}
