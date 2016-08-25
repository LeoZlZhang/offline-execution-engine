package leo.carnival.worker.filter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AdvanceFileFilter implements com.vipabc.vliveshow.TestExecutionEngine.Util.Worker.Filter<File>, Processor<File, List<File>> {
    private FileFilter baseFilter;
    private String[] regex;

    public AdvanceFileFilter(FileFilter baseFilter, String... reg) {
        this.baseFilter = baseFilter;
        this.regex = reg;
    }

    @Override
    public boolean evaluate(File file) {
        if (baseFilter == null)
            return false;
        for (String reg : regex) {
            if (!Pattern.matches(reg, file.getName()))
                return false;
        }
        return true;
    }

    @Override
    public List<File> process(File file) {
        List<File> rtnList = new ArrayList<>();
        if (file.isDirectory())
            FileSearchUtil.findAllFile(file, rtnList, baseFilter);
        else if(baseFilter.evaluate(file))
            rtnList.add(file);
        return rtnList;
    }

}
